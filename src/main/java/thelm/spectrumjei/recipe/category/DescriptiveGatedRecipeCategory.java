package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.spectrum.recipe.DescriptiveGatedRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import thelm.jeidrawables.JEIDrawables;

/**
 * Based on IngredientInfoRecipeCategory
 */
public class DescriptiveGatedRecipeCategory<R extends DescriptiveGatedRecipe> extends AbstractGatedRecipeCategory<R> {

	public DescriptiveGatedRecipeCategory(RecipeType<R> recipeType, Text title) {
		super(recipeType, title);
	}

	@Override
	public int getHeight() {
		return 125;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		ItemStack stack = new ItemStack(recipe.getItem());
		addItem(builder, RecipeIngredientRole.INPUT, 60, 1, stack, JEIDrawables.SLOT, visible);
		builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStack(stack);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			TextRenderer font = font();
			List<OrderedText> lines = font.wrapLines(recipe.getDescription(), getWidth());
			for(int i = 0; i < lines.size(); ++i) {
				font.draw(poseStack, lines.get(i), 0, 22 + i * 10, 0x3F3F3F);
			}
		}
	}
}
