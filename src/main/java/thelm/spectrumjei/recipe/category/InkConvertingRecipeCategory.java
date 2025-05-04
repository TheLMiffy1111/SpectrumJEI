package thelm.spectrumjei.recipe.category;

import de.dafuqs.spectrum.recipe.ink_converting.InkConvertingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on InkConvertingEmiRecipeGated
 */
public class InkConvertingRecipeCategory extends AbstractGatedRecipeCategory<InkConvertingRecipe> {

	public static final Text TITLE = new TranslatableText("container.spectrum.rei.ink_converting.title");

	public InkConvertingRecipeCategory() {
		super(SpectrumJEI.INK_CONVERTING, TITLE);
	}

	@Override
	public int getHeight() {
		return 20;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, InkConvertingRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		addItem(builder, RecipeIngredientRole.INPUT, 1, 2, recipe.getIngredients().get(0), SpectrumJEI.SLOT, visible);
	}

	@Override
	public void draw(InkConvertingRecipe recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			SpectrumJEI.RECIPE_ARROW.draw(poseStack, 22, 2);
			TextRenderer font = font();
			Text colorComponent = new TranslatableText("container.spectrum.rei.ink_converting.color", recipe.getInkColor().getName());
			Text amountComponent = new TranslatableText("container.spectrum.rei.ink_converting.amount", recipe.getInkAmount());
			font.draw(poseStack, colorComponent, 50, 1, 0x3F3F3F);
			font.draw(poseStack, amountComponent, 50, 11, 0x3F3F3F);
		}
	}
}
