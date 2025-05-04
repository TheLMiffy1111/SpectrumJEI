package thelm.spectrumjei.recipe.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thelm.spectrumjei.SpectrumJEI;
import thelm.spectrumjei.recipe.BlockConversionWithChanceRecipe;

/**
 * Based on BlockToBlockWithChanceEmiRecipe
 */
public class BlockConversionWithChanceRecipeCategory extends AbstractUnlockableRecipeCategory<BlockConversionWithChanceRecipe> {

	public final Identifier advancement;

	public BlockConversionWithChanceRecipeCategory(RecipeType<BlockConversionWithChanceRecipe> recipeType, Text title, Identifier advancement) {
		super(recipeType, title);
		this.advancement = advancement;
	}

	@Override
	public int getHeight() {
		return 39;
	}

	@Override
	public boolean isUnlocked(BlockConversionWithChanceRecipe recipe) {
		return hasAdvancement(advancement);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, BlockConversionWithChanceRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		addSlot(builder, RecipeIngredientRole.INPUT, 30, 5, SpectrumJEI.SLOT, visible).addIngredientsUnsafe(recipe.inputIngredient());
		addSlot(builder, RecipeIngredientRole.OUTPUT, 86, 5, SpectrumJEI.OUTPUT_SLOT, visible).addIngredientsUnsafe(recipe.outputIngredient());
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, BlockConversionWithChanceRecipe recipe, IFocusGroup focuses) {
		if(isVisible(recipe)) {
			builder.addDrawable(SpectrumJEI.RECIPE_ARROW, 53, 5);
		}
	}

	@Override
	public void draw(BlockConversionWithChanceRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipe)) {
			TextRenderer font = font();
			Text chanceComponent = Text.translatable("container.spectrum.rei.chance", recipe.chance() * 100);
			guiGraphics.drawText(font, chanceComponent, getWidth() / 2 - font.getWidth(chanceComponent) / 2, 29, 0x3F3F3F, false);
		}
	}

	@Override
	public Identifier getRegistryName(BlockConversionWithChanceRecipe recipe) {
		Identifier blockKey = Registries.BLOCK.getId(recipe.input().getBlock());
		return new Identifier("%s/%s/%s".formatted(recipeType.getUid(), blockKey.getNamespace(), blockKey.getPath()));
	}
}
