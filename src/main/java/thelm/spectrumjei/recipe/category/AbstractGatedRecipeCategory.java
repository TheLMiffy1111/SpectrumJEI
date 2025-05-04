package thelm.spectrumjei.recipe.category;

import de.dafuqs.spectrum.api.recipe.GatedRecipe;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public abstract class AbstractGatedRecipeCategory<R extends GatedRecipe<?>> extends AbstractUnlockableRecipeCategory<R> {

	public AbstractGatedRecipeCategory(RecipeType<R> recipeType, Text title) {
		super(recipeType, title);
	}

	@Override
	public boolean isUnlocked(R recipe) {
		return hasAdvancement(recipe.getRecipeTypeUnlockIdentifier()) && hasAdvancement(recipe.getRequiredAdvancementIdentifier());
	}

	@Override
	public boolean isVisible(R recipe) {
		return super.isVisible(recipe) && !recipe.isSecret();
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		if(!isUnlocked(recipe)) {
			drawLockedText(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		}
		else if(recipe.isSecret()) {
			drawSecretText(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		}
	}

	public void drawSecretText(R recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		TextRenderer font = font();
		if(recipe.getSecretHintText() == null) {
			guiGraphics.drawText(font, SECRET, getWidth() / 2 - font.getWidth(SECRET) / 2, getHeight() / 2 - 4, 0x3F3F3F, false);
		}
		else {
			Text hintComponent = recipe.getSecretHintText();
			guiGraphics.drawText(font, SECRET_HINT, getWidth() / 2 - font.getWidth(SECRET_HINT) / 2, getHeight() / 2 - 9, 0x3F3F3F, false);
			guiGraphics.drawText(font, hintComponent, getWidth() / 2 - font.getWidth(hintComponent) / 2, getHeight() / 2 + 1, 0x3F3F3F, false);
		}
	}
}
