package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.spectrum.recipe.cinderhearth.CinderhearthRecipe;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Pair;
import thelm.spectrumjei.SpectrumJEI;
import thelm.spectrumjei.gui.render.RecipeArrowDrawable;
import thelm.spectrumjei.gui.render.RecipeFlameDrawable;

/**
 * Based on CinderhearthEmiRecipeGated
 */
public class CinderhearthRecipeCategory extends AbstractGatedRecipeCategory<CinderhearthRecipe> {

	public static final Text TITLE = new TranslatableText("block.spectrum.cinderhearth");

	public CinderhearthRecipeCategory() {
		super(SpectrumJEI.CINDERHEARTH, TITLE);
	}

	@Override
	public int getHeight() {
		return 54;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, CinderhearthRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		addItem(builder, RecipeIngredientRole.INPUT, 4, 1, recipe.getIngredients().get(0), SpectrumJEI.SLOT, visible);
		List<Pair<ItemStack, Float>> outputs = recipe.getOutputsWithChance();
		for(int i = 0; i < 3; ++i) {
			IIngredientAcceptor<?> acceptor = addSlot(builder, RecipeIngredientRole.OUTPUT, 58 + i * 28, 10, SpectrumJEI.OUTPUT_SLOT, visible);
			if(i < outputs.size()) {
				acceptor.addItemStack(outputs.get(i).getLeft());
			}
		}
	}

	@Override
	public void draw(CinderhearthRecipe recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			RecipeArrowDrawable.of(recipe.getCraftingTime() * 50).draw(poseStack, 26, 10);
			RecipeFlameDrawable.DEFAULT.draw(poseStack, 4, 19);
			TextRenderer font = font();
			List<Pair<ItemStack, Float>> outputs = recipe.getOutputsWithChance();
			for(int i = 0; i < outputs.size(); ++i) {
				if(outputs.get(i).getRight() < 1) {
					Text chanceComponent = new LiteralText((int)(outputs.get(i).getRight() * 100) + "%");
					font.draw(poseStack, chanceComponent, 67 + i * 28 - font.getWidth(chanceComponent) / 2, 32, 0x3F3F3F);
				}
			}
			Text timeComponent = getTimeComponent(recipe.getCraftingTime(), recipe.getExperience());
			font.draw(poseStack, timeComponent, getWidth() / 2 - font.getWidth(timeComponent) / 2, 44, 0x3F3F3F);
		}
	}
}
