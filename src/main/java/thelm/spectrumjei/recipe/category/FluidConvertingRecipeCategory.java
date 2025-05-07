package thelm.spectrumjei.recipe.category;

import de.dafuqs.spectrum.recipe.fluid_converting.FluidConvertingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.text.Text;
import thelm.jeidrawables.JEIDrawables;

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
		addItem(builder, RecipeIngredientRole.INPUT, 30, 5, recipe.getIngredients().get(0), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.OUTPUT, 86, 5, recipe.getOutput(registryAccess()), JEIDrawables.OUTPUT_SLOT, visible);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, R recipe, IFocusGroup focuses) {
		if(isVisible(recipe)) {
			builder.addDrawable(JEIDrawables.RECIPE_ARROW, 53, 5);
		}
	}
}
