package thelm.spectrumjei.recipe.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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
	public void draw(BlockConversionRecipe recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			JEIDrawables.RECIPE_ARROW.draw(poseStack, 53, 5);
		}
	}

	@Override
	public Identifier getRegistryName(BlockConversionRecipe recipe) {
		return Registry.BLOCK.getId(recipe.input().getBlock());
	}
}
