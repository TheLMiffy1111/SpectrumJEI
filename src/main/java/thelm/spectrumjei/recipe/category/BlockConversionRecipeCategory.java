package thelm.spectrumjei.recipe.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thelm.jeidrawables.JEIDrawables;
import thelm.spectrumjei.recipe.BlockConversionRecipe;

/**
 * Based on BlockToBlockWithChanceEmiRecipe
 */
public class BlockConversionRecipeCategory extends AbstractUnlockableRecipeCategory<BlockConversionRecipe> {

	public final Identifier advancement;

	public BlockConversionRecipeCategory(RecipeType<BlockConversionRecipe> recipeType, Text title, Identifier advancement) {
		super(recipeType, title);
		this.advancement = advancement;
	}

	@Override
	public int getHeight() {
		return 26;
	}

	@Override
	public boolean isUnlocked(BlockConversionRecipe recipe) {
		return hasAdvancement(advancement);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, BlockConversionRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		addSlot(builder, RecipeIngredientRole.INPUT, 30, 5, JEIDrawables.SLOT, visible).addIngredientsUnsafe(recipe.inputIngredient());
		addSlot(builder, RecipeIngredientRole.OUTPUT, 86, 5, JEIDrawables.OUTPUT_SLOT, visible).addIngredientsUnsafe(recipe.outputIngredient());
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, BlockConversionRecipe recipe, IFocusGroup focuses) {
		if(isVisible(recipe)) {
			builder.addDrawable(JEIDrawables.RECIPE_ARROW, 53, 5);
		}
	}

	@Override
	public Identifier getRegistryName(BlockConversionRecipe recipe) {
		Identifier blockKey = Registries.BLOCK.getId(recipe.input().getBlock());
		return new Identifier("%s/%s/%s".formatted(recipeType.getUid(), blockKey.getNamespace(), blockKey.getPath()));
	}
}
