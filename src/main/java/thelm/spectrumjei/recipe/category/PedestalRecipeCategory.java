package thelm.spectrumjei.recipe.category;

import java.util.ArrayList;
import java.util.List;

import de.dafuqs.spectrum.enums.BuiltinGemstoneColor;
import de.dafuqs.spectrum.enums.GemstoneColor;
import de.dafuqs.spectrum.enums.PedestalRecipeTier;
import de.dafuqs.spectrum.inventories.PedestalScreen;
import de.dafuqs.spectrum.recipe.pedestal.PedestalCraftingRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.id.incubus_core.recipe.IngredientStack;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import thelm.jeidrawables.JEIDrawables;
import thelm.jeidrawables.gui.render.CyclingDrawable;
import thelm.jeidrawables.gui.render.IngredientDrawable;
import thelm.jeidrawables.gui.render.ResourceDrawable;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on PedestalCraftingEmiRecipeGated
 */
public class PedestalRecipeCategory extends AbstractGatedRecipeCategory<PedestalCraftingRecipe> {

	public static final Text TITLE_BASIC = new TranslatableText("block.spectrum.pedestal");
	public static final Text TITLE_SIMPLE = new TranslatableText("multiblock.spectrum.pedestal.simple_structure");
	public static final Text TITLE_ADVANCED = new TranslatableText("multiblock.spectrum.pedestal.advanced_structure");
	public static final Text TITLE_COMPLEX = new TranslatableText("multiblock.spectrum.pedestal.complex_structure");

	public static final IDrawable BASIC_ICON = new CyclingDrawable(1000,
			new IngredientDrawable<>(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_TOPAZ)),
			new IngredientDrawable<>(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST)),
			new IngredientDrawable<>(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_CITRINE)));

	public final PedestalRecipeTier tier;
	public final int powderSlotCount;

	public final Identifier background;
	public final ResourceDrawable[] inputSlots;
	public final ResourceDrawable[] powderSlots;
	public final ResourceDrawable outputSlot;
	public final ResourceDrawable tierOverlay;

	public PedestalRecipeCategory(PedestalRecipeTier tier) {
		super(getRecipeType(tier), getTitle(tier));
		this.tier = tier;
		powderSlotCount = switch(tier) {
		case BASIC, SIMPLE -> 3;
		case ADVANCED -> 4;
		case COMPLEX -> 5;
		};

		background = PedestalScreen.getBackgroundTextureForTier(tier);
		inputSlots = new ResourceDrawable[9];
		for(int y = 0; y < 3; ++y) {
			for(int x = 0; x < 3; ++x) {
				inputSlots[y * 3 + x] = new ResourceDrawable(background, 29 + x * 18, 18 + y * 18, 18, 18);
			}
		}
		int powderSlotU = 88 - powderSlotCount * 9;
		powderSlots = new ResourceDrawable[powderSlotCount];
		for(int i = 0; i < powderSlotCount; ++i) {
			powderSlots[i] = new ResourceDrawable(background, powderSlotU + i * 18, 76, 18, 18);
		}
		outputSlot = new ResourceDrawable(background, 122, 32, 26, 26);
		tierOverlay = new ResourceDrawable(background, 200, 0, 40, 16);
	}

	public static RecipeType<PedestalCraftingRecipe> getRecipeType(PedestalRecipeTier tier) {
		return switch(tier) {
		case BASIC -> SpectrumJEI.PEDESTAL_BASIC;
		case SIMPLE -> SpectrumJEI.PEDESTAL_SIMPLE;
		case ADVANCED -> SpectrumJEI.PEDESTAL_ADVANCED;
		case COMPLEX -> SpectrumJEI.PEDESTAL_COMPLEX;
		};
	}

	public static Text getTitle(PedestalRecipeTier tier) {
		return switch(tier) {
		case BASIC -> TITLE_BASIC;
		case SIMPLE -> TITLE_SIMPLE;
		case ADVANCED -> TITLE_ADVANCED;
		case COMPLEX -> TITLE_COMPLEX;
		};
	}

	@Override
	public int getHeight() {
		return 90;
	}

	@Override
	public IDrawable getIcon() {
		return tier == PedestalRecipeTier.BASIC ? BASIC_ICON : null;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, PedestalCraftingRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		int powderSlotX = 1 + getWidth() / 2 - powderSlotCount * 9;
		List<IIngredientAcceptor<?>> gridSlots = new ArrayList<>(9);
		for(int y = 0; y < 3; ++y) {
			for(int x = 0; x < 3; ++x) {
				gridSlots.add(addSlot(builder, RecipeIngredientRole.INPUT, 7 + x * 18, 1 + y * 18, inputSlots[y * 3 + x], visible));
			}
		}
		List<IngredientStack> ingredients = recipe.getIngredientStacks();
		for(int i = 0; i < ingredients.size(); ++i) {
			gridSlots.get(getGridSlotId(recipe.getWidth(), i)).addItemStacks(ingredients.get(i).getStacks());
		}
		for(int i = 0; i < powderSlotCount; ++i) {
			IIngredientAcceptor<?> slot = addSlot(builder, RecipeIngredientRole.INPUT, powderSlotX + i * 18, 60, powderSlots[i], visible);
			GemstoneColor color = BuiltinGemstoneColor.values()[i];
			int powderAmount = recipe.getGemstonePowderInputs().getOrDefault(color, 0);
			if(powderAmount > 0) {
				slot.addItemStack(new ItemStack(color.getGemstonePowderItem(), powderAmount));
			}
		}
		addItem(builder, RecipeIngredientRole.OUTPUT, 101, 19, recipe.getOutput(), outputSlot, visible);
	}

	public static int getGridSlotId(int recipeWidth, int index) {
		int x = index % recipeWidth;
		int y = (index - x) / recipeWidth;
		return 3 * y + x;
	}

	@Override
	public void draw(PedestalCraftingRecipe recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			tierOverlay.draw(poseStack, 88, 38);
			JEIDrawables.recipeArrow(recipe.getCraftingTime() * 50).draw(poseStack, 67, 19);
			TextRenderer font = font();
			Text timeComponent = getTimeComponent(recipe.getCraftingTime(), recipe.getExperience());
			font.draw(poseStack, timeComponent, getWidth() / 2 - font.getWidth(timeComponent) / 2, 80, 0x3F3F3F);
		}
	}
}
