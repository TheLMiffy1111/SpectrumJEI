package thelm.spectrumjei.recipe.category;

import de.dafuqs.spectrum.recipe.ink_converting.InkConvertingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on InkConvertingEmiRecipeGated
 */
public class InkConvertingRecipeCategory extends AbstractGatedRecipeCategory<InkConvertingRecipe> {

	public static final Text TITLE = Text.translatable("container.spectrum.rei.ink_converting.title");

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
	public void createRecipeExtras(IRecipeExtrasBuilder builder, InkConvertingRecipe recipe, IFocusGroup focuses) {
		if(isVisible(recipe)) {
			builder.addDrawable(SpectrumJEI.RECIPE_ARROW, 22, 2);
		}
	}

	@Override
	public void draw(InkConvertingRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipe)) {
			TextRenderer font = font();
			Text colorComponent = Text.translatable("container.spectrum.rei.ink_converting.color", recipe.getInkColor().getName());
			Text amountComponent = Text.translatable("container.spectrum.rei.ink_converting.amount", recipe.getInkAmount());
			guiGraphics.drawText(font, colorComponent, 50, 1, 0x3F3F3F, false);
			guiGraphics.drawText(font, amountComponent, 50, 11, 0x3F3F3F, false);
		}
	}
}
