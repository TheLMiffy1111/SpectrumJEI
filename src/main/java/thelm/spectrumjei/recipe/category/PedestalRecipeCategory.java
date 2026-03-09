package thelm.spectrumjei.recipe.category;

import java.util.ArrayList;
import java.util.List;

import de.dafuqs.spectrum.api.item.GemstoneColor;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.blocks.pedestal.BuiltinGemstoneColor;
import de.dafuqs.spectrum.inventories.PedestalScreen;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipe;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipeTier;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import thelm.jeidrawables.JEIDrawables;
import thelm.jeidrawables.gui.render.CyclingDrawable;
import thelm.jeidrawables.gui.render.IngredientDrawable;
import thelm.jeidrawables.gui.render.ResourceDrawable;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on PedestalCraftingEmiRecipeGated
 */
public class PedestalRecipeCategory extends AbstractGatedRecipeCategory<PedestalRecipe> {

	public static final Component TITLE_BASIC = Component.translatable("block.spectrum.pedestal");
	public static final Component TITLE_SIMPLE = Component.translatable("multiblock.spectrum.pedestal_simple");
	public static final Component TITLE_ADVANCED = Component.translatable("multiblock.spectrum.pedestal_advanced");
	public static final Component TITLE_COMPLEX = Component.translatable("multiblock.spectrum.pedestal_complex");

	public static final IDrawable BASIC_ICON = new CyclingDrawable(1000,
			new IngredientDrawable<>(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_TOPAZ)),
			new IngredientDrawable<>(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST)),
			new IngredientDrawable<>(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_CITRINE)));

	public final PedestalRecipeTier tier;
	public final int powderSlotCount;

	public final ResourceLocation background;
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

	public static RecipeType<RecipeHolder<PedestalRecipe>> getRecipeType(PedestalRecipeTier tier) {
		return switch(tier) {
		case BASIC -> SpectrumJEI.PEDESTAL_BASIC;
		case SIMPLE -> SpectrumJEI.PEDESTAL_SIMPLE;
		case ADVANCED -> SpectrumJEI.PEDESTAL_ADVANCED;
		case COMPLEX -> SpectrumJEI.PEDESTAL_COMPLEX;
		};
	}

	public static Component getTitle(PedestalRecipeTier tier) {
		return switch(tier) {
		case BASIC -> TITLE_BASIC;
		case SIMPLE -> TITLE_SIMPLE;
		case ADVANCED -> TITLE_ADVANCED;
		case COMPLEX -> TITLE_COMPLEX;
		};
	}

	@Override
	public int getHeight() {
		return 89;
	}

	@Override
	public IDrawable getIcon() {
		return tier == PedestalRecipeTier.BASIC ? BASIC_ICON : null;
	}

	@Override
	public boolean isUnlocked(RecipeHolder<PedestalRecipe> recipeHolder) {
		return super.isUnlocked(recipeHolder) && recipeHolder.value().getTier().hasUnlocked(Minecraft.getInstance().player);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<PedestalRecipe> recipeHolder, IFocusGroup focuses) {
		boolean visible = isVisible(recipeHolder);
		PedestalRecipe recipe = recipeHolder.value();
		int powderSlotX = 1 + getWidth() / 2 - powderSlotCount * 9;
		List<IIngredientAcceptor<?>> gridSlots = new ArrayList<>(9);
		for(int y = 0; y < 3; ++y) {
			for(int x = 0; x < 3; ++x) {
				gridSlots.add(addSlot(builder, RecipeIngredientRole.INPUT, 7 + x * 18, 1 + y * 18, inputSlots[y * 3 + x], visible));
			}
		}
		List<IngredientStack> ingredients = recipe.getIngredientStacks();
		for(int i = 0; i < ingredients.size(); ++i) {
			gridSlots.get(recipe.getGridSlotId(i)).addItemStacks(ingredients.get(i).getItems().toList());
		}
		for(int i = 0; i < powderSlotCount; ++i) {
			IIngredientAcceptor<?> slot = addSlot(builder, RecipeIngredientRole.INPUT, powderSlotX + i * 18, 60, powderSlots[i], visible);
			GemstoneColor color = BuiltinGemstoneColor.values()[i];
			int powderAmount = recipe.getPowderInputs().getOrDefault(color, 0);
			if(powderAmount > 0) {
				slot.addItemStack(new ItemStack(color.getGemstonePowderItem(), powderAmount));
			}
		}
		addItem(builder, RecipeIngredientRole.OUTPUT, 101, 19, recipe.getResultItem(registryAccess()), outputSlot, visible);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<PedestalRecipe> recipeHolder, IFocusGroup focuses) {
		if(isVisible(recipeHolder)) {
			PedestalRecipe recipe = recipeHolder.value();
			builder.addDrawable(tierOverlay, 88, 38);
			builder.addDrawable(JEIDrawables.recipeArrow(recipe.getCraftingTime() * 50), 67, 19);
			if(recipe.isShapeless()) {
				builder.addDrawable(JEIDrawables.SHAPELESS_ICON, 121, 0);
			}
		}
	}

	@Override
	public void draw(RecipeHolder<PedestalRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipeHolder, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipeHolder)) {
			PedestalRecipe recipe = recipeHolder.value();
			Font font = font();
			Component timeComponent = getTimeComponent(recipe.getCraftingTime(), recipe.getExperience());
			guiGraphics.drawString(font, timeComponent, getWidth() / 2 - font.width(timeComponent) / 2, 80, 0x3F3F3F, false);
		}
	}
}
