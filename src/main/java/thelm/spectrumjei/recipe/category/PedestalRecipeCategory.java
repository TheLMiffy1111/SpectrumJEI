package thelm.spectrumjei.recipe.category;

import java.util.ArrayList;
import java.util.List;

import de.dafuqs.matchbooks.recipe.IngredientStack;
import de.dafuqs.spectrum.api.item.GemstoneColor;
import de.dafuqs.spectrum.inventories.PedestalScreen;
import de.dafuqs.spectrum.recipe.pedestal.BuiltinGemstoneColor;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipe;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipeTier;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thelm.spectrumjei.SpectrumJEI;
import thelm.spectrumjei.gui.render.RecipeArrowDrawable;
import thelm.spectrumjei.gui.render.ResourceDrawable;

/**
 * Based on PedestalCraftingEmiRecipeGated
 */
public class PedestalRecipeCategory extends AbstractGatedRecipeCategory<PedestalRecipe> {

	public static final Text TITLE_BASIC = Text.translatable("block.spectrum.pedestal");
	public static final Text TITLE_SIMPLE = Text.translatable("multiblock.spectrum.pedestal_simple");
	public static final Text TITLE_ADVANCED = Text.translatable("multiblock.spectrum.pedestal_advanced");
	public static final Text TITLE_COMPLEX = Text.translatable("multiblock.spectrum.pedestal_complex");

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
		powderSlotCount = tier.getPowderSlotCount();

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

	public static RecipeType<PedestalRecipe> getRecipeType(PedestalRecipeTier tier) {
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
	public boolean isUnlocked(PedestalRecipe recipe) {
		return super.isUnlocked(recipe) && recipe.getTier().hasUnlocked(MinecraftClient.getInstance().player);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, PedestalRecipe recipe, IFocusGroup focuses) {
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
			gridSlots.get(recipe.getGridSlotId(i)).addItemStacks(ingredients.get(i).getStacks());
		}
		for(int i = 0; i < powderSlotCount; ++i) {
			IIngredientAcceptor<?> slot = addSlot(builder, RecipeIngredientRole.INPUT, powderSlotX + i * 18, 60, powderSlots[i], visible);
			GemstoneColor color = BuiltinGemstoneColor.values()[i];
			int powderAmount = recipe.getPowderInputs().getOrDefault(color, 0);
			if(powderAmount > 0) {
				slot.addItemStack(new ItemStack(color.getGemstonePowderItem(), powderAmount));
			}
		}
		addItem(builder, RecipeIngredientRole.OUTPUT, 101, 19, recipe.getOutput(registryAccess()), outputSlot, visible);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, PedestalRecipe recipe, IFocusGroup focuses) {
		if(isVisible(recipe)) {
			builder.addDrawable(tierOverlay, 88, 38);
			builder.addDrawable(RecipeArrowDrawable.of(recipe.getCraftingTime() * 50), 67, 19);
			if(recipe.isShapeless()) {
				builder.addDrawable(SpectrumJEI.SHAPELESS, 121, 0);
			}
		}
	}

	@Override
	public void draw(PedestalRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipe)) {
			TextRenderer font = font();
			Text timeComponent = getTimeComponent(recipe.getCraftingTime(), recipe.getExperience());
			guiGraphics.drawText(font, timeComponent, getWidth() / 2 - font.getWidth(timeComponent) / 2, 80, 0x3F3F3F, false);
		}
	}
}
