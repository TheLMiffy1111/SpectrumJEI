package thelm.spectrumjei.recipe.category;

import de.dafuqs.spectrum.recipe.fluid_converting.FluidConvertingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on FluidConvertingEmiRecipeGated
 */
public class FluidConvertingRecipeCategory<R extends FluidConvertingRecipe> extends AbstractGatedRecipeCategory<R> {

	public FluidConvertingRecipeCategory(RecipeType<R> recipeType, Text title) {
		super(recipeType, title);
	}

	@Override
	public int getHeight() {
		return 26;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		addItem(builder, RecipeIngredientRole.INPUT, 30, 5, recipe.getIngredients().get(0), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.OUTPUT, 86, 5, recipe.getOutput(), SpectrumJEI.OUTPUT_SLOT, visible);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			SpectrumJEI.RECIPE_ARROW.draw(poseStack, 53, 5);
		}
	}
}
