package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.matchbooks.recipe.IngredientStack;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thelm.jeidrawables.JEIDrawables;
import thelm.jeidrawables.gui.render.ResourceDrawable;

/**
 * Based on PotionWorkshopEmiRecipeGated
 */
public class PotionWorkshopRecipeCategory<R extends PotionWorkshopRecipe> extends AbstractGatedRecipeCategory<R> {

	public static final Identifier BACKGROUND = SpectrumCommon.locate("textures/gui/container/potion_workshop_3_slots.png");
	public static final ResourceDrawable BUBBLES = new ResourceDrawable(BACKGROUND, 176, 0, 11, 27);

	public PotionWorkshopRecipeCategory(RecipeType<R> recipeType, Text title) {
		super(recipeType, title);
	}

	@Override
	public int getHeight() {
		return 66;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		List<IngredientStack> ingredients = recipe.getIngredientStacks();
		addItem(builder, RecipeIngredientRole.INPUT, 31, 49, ingredients.get(0).getStacks(), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 78, 5, ingredients.get(1).getStacks(), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 31, 1, ingredients.get(2).getStacks(), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 13, 25, ingredients.get(3).getStacks(), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 49, 25, ingredients.get(4).getStacks(), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.OUTPUT, 107, 25, recipe.getOutput(registryAccess()), JEIDrawables.SLOT, visible);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, R recipe, IFocusGroup focuses) {
		if(isVisible(recipe)) {
			builder.addDrawable(BUBBLES, 33, 20);
			builder.addDrawable(JEIDrawables.recipeArrow(recipe.getCraftingTime() * 50), 75, 25);
		}
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipe)) {
			TextRenderer font = font();
			Text timeComponent = getTimeComponent(recipe.getCraftingTime());
			guiGraphics.drawText(font, timeComponent, 52, 56, 0x3F3F3F, false);
		}
	}
}
