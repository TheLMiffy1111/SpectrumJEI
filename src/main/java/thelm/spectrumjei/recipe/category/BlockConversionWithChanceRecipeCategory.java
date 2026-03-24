package thelm.spectrumjei.recipe.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import thelm.jeidrawables.JEIDrawables;
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
		return 38;
	}

	@Override
	public boolean isUnlocked(BlockConversionWithChanceRecipe recipe) {
		return hasAdvancement(advancement);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, BlockConversionWithChanceRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		addSlot(builder, RecipeIngredientRole.INPUT, 30, 5, JEIDrawables.SLOT, visible).addIngredientsUnsafe(recipe.inputIngredient());
		addSlot(builder, RecipeIngredientRole.OUTPUT, 86, 5, JEIDrawables.OUTPUT_SLOT, visible).addIngredientsUnsafe(recipe.outputIngredient());
	}

	@Override
	public void draw(BlockConversionWithChanceRecipe recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			JEIDrawables.RECIPE_ARROW.draw(poseStack, 53, 5);
			TextRenderer font = font();
			Text chanceComponent = new TranslatableText("container.spectrum.rei.chance", recipe.chance() * 100);
			font.draw(poseStack, chanceComponent, getWidth() / 2 - font.getWidth(chanceComponent) / 2, 29, 0x3F3F3F);
		}
	}

	@Override
	public Identifier getRegistryName(BlockConversionWithChanceRecipe recipe) {
		return Registry.BLOCK.getId(recipe.input().getBlock());
	}
}
