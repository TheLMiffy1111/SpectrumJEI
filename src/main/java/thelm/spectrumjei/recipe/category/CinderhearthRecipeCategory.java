package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.spectrum.recipe.StackWithChance;
import de.dafuqs.spectrum.recipe.cinderhearth.CinderhearthRecipe;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import thelm.jeidrawables.JEIDrawables;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on CinderhearthEmiRecipeGated
 */
public class CinderhearthRecipeCategory extends AbstractGatedRecipeCategory<CinderhearthRecipe> {

	public static final Component TITLE = Component.translatable("block.spectrum.cinderhearth");

	public static final IDrawable FLAME = JEIDrawables.flame(10000);

	public CinderhearthRecipeCategory() {
		super(SpectrumJEI.CINDERHEARTH, TITLE);
	}

	@Override
	public int getHeight() {
		return 51;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CinderhearthRecipe> recipeHolder, IFocusGroup focuses) {
		boolean visible = isVisible(recipeHolder);
		CinderhearthRecipe recipe = recipeHolder.value();
		addItem(builder, RecipeIngredientRole.INPUT, 4, 1, recipe.getIngredientStacks().get(0).getItems().toList(), JEIDrawables.SLOT, visible);
		List<StackWithChance> outputs = recipe.getResultsWithChance();
		for(int i = 0; i < 3; ++i) {
			IIngredientAcceptor<?> acceptor = addSlot(builder, RecipeIngredientRole.OUTPUT, 58 + i * 28, 10, JEIDrawables.OUTPUT_SLOT, visible);
			if(i < outputs.size()) {
				acceptor.addItemStack(outputs.get(i).stack());
			}
		}
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<CinderhearthRecipe> recipeHolder, IFocusGroup focuses) {
		if(isVisible(recipeHolder)) {
			CinderhearthRecipe recipe = recipeHolder.value();
			builder.addDrawable(JEIDrawables.recipeArrow(recipe.getCraftingTime() * 50), 26, 10);
			builder.addDrawable(FLAME, 4, 19);
		}
	}

	@Override
	public void draw(RecipeHolder<CinderhearthRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipeHolder, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipeHolder)) {
			CinderhearthRecipe recipe = recipeHolder.value();
			Font font = font();
			List<StackWithChance> outputs = recipe.getResultsWithChance();
			for(int i = 0; i < outputs.size(); ++i) {
				if(outputs.get(i).chance() < 1) {
					Component chanceComponent = Component.literal((int)(outputs.get(i).chance() * 100) + "%");
					guiGraphics.drawString(font, chanceComponent, 67 + i * 28 - font.width(chanceComponent) / 2, 32, 0x3F3F3F, false);
				}
			}
			Component timeComponent = getTimeComponent(recipe.getCraftingTime(), recipe.getExperience());
			guiGraphics.drawString(font, timeComponent, getWidth() / 2 - font.width(timeComponent) / 2, 42, 0x3F3F3F, false);
		}
	}
}
