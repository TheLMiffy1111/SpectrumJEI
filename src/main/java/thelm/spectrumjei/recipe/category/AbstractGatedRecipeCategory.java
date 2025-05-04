package thelm.spectrumjei.recipe.category;

import de.dafuqs.spectrum.recipe.GatedRecipe;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class AbstractGatedRecipeCategory<R extends GatedRecipe> extends AbstractUnlockableRecipeCategory<R> {

	public AbstractGatedRecipeCategory(RecipeType<R> recipeType, Text title) {
		super(recipeType, title);
	}

	@Override
	public boolean isHandled(R recipe) {
		return !recipe.isSecret();
	}

	@Override
	public boolean isUnlocked(R recipe) {
		return hasAdvancement(recipe.getRecipeTypeUnlockIdentifier()) && hasAdvancement(recipe.getRequiredAdvancementIdentifier());
	}

	@Override
	public boolean isVisible(R recipe) {
		return super.isVisible(recipe);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		if(!isUnlocked(recipe)) {
			drawLockedText(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		}
	}
}
