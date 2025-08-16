package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.spectrum.recipe.cinderhearth.CinderhearthRecipe;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import thelm.jeidrawables.JEIDrawables;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on CinderhearthEmiRecipeGated
 */
public class CinderhearthRecipeCategory extends AbstractGatedRecipeCategory<CinderhearthRecipe> {

	public static final Text TITLE = Text.translatable("block.spectrum.cinderhearth");

	public static final IDrawable FLAME = JEIDrawables.flame(10000);

	public CinderhearthRecipeCategory() {
		super(SpectrumJEI.CINDERHEARTH, TITLE);
	}

	@Override
	public int getHeight() {
		return 51;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, CinderhearthRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		addItem(builder, RecipeIngredientRole.INPUT, 4, 1, recipe.getIngredientStacks().get(0).getStacks(), JEIDrawables.SLOT, visible);
		List<Pair<ItemStack, Float>> outputs = recipe.getOutputsWithChance(registryAccess());
		for(int i = 0; i < 3; ++i) {
			IIngredientAcceptor<?> acceptor = addSlot(builder, RecipeIngredientRole.OUTPUT, 58 + i * 28, 10, JEIDrawables.OUTPUT_SLOT, visible);
			if(i < outputs.size()) {
				acceptor.addItemStack(outputs.get(i).getLeft());
			}
		}
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, CinderhearthRecipe recipe, IFocusGroup focuses) {
		if(isVisible(recipe)) {
			builder.addDrawable(JEIDrawables.recipeArrow(recipe.getCraftingTime() * 50), 26, 10);
			builder.addDrawable(FLAME, 4, 19);
		}
	}

	@Override
	public void draw(CinderhearthRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipe)) {
			TextRenderer font = font();
			List<Pair<ItemStack, Float>> outputs = recipe.getOutputsWithChance(registryAccess());
			for(int i = 0; i < outputs.size(); ++i) {
				if(outputs.get(i).getRight() < 1) {
					Text chanceComponent = Text.literal((int)(outputs.get(i).getRight() * 100) + "%");
					guiGraphics.drawText(font, chanceComponent, 67 + i * 28 - font.getWidth(chanceComponent) / 2, 32, 0x3F3F3F, false);
				}
			}
			Text timeComponent = getTimeComponent(recipe.getCraftingTime(), recipe.getExperience());
			guiGraphics.drawText(font, timeComponent, getWidth() / 2 - font.getWidth(timeComponent) / 2, 42, 0x3F3F3F, false);
		}
	}
}
