package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.items.magic_items.KnowledgeGemItem;
import de.dafuqs.spectrum.recipe.enchanter.EnchanterRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import thelm.jeidrawables.JEIDrawables;
import thelm.jeidrawables.gui.render.ResourceDrawable;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on EnchanterEmiRecipeGated
 */
public class EnchanterRecipeCategory extends AbstractGatedRecipeCategory<EnchanterRecipe> {

	public static final Text TITLE = new TranslatableText("container.spectrum.rei.enchanting.title");

	public static final Identifier BACKGROUND = SpectrumCommon.locate("textures/gui/container/enchanter.png");
	public static final ResourceDrawable ALTAR = new ResourceDrawable(BACKGROUND, 0, 0, 54, 54);

	public EnchanterRecipeCategory() {
		super(SpectrumJEI.ENCHANTER, TITLE);
	}

	@Override
	public int getHeight() {
		return 80;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, EnchanterRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		List<Ingredient> ingredients = recipe.getIngredients();
		addItem(builder, RecipeIngredientRole.INPUT, 113, 7, KnowledgeGemItem.getKnowledgeDropStackWithXP(recipe.getRequiredExperience(), true), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.CATALYST, 113, 53, new ItemStack(SpectrumBlocks.ENCHANTER), visible);
		addItem(builder, RecipeIngredientRole.INPUT, 34, 32, ingredients.get(0), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 21, 1, ingredients.get(1), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 47, 1, ingredients.get(2), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 65, 19, ingredients.get(3), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 65, 45, ingredients.get(4), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 47, 63, ingredients.get(5), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 21, 63, ingredients.get(6), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 3, 45, ingredients.get(7), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 3, 19, ingredients.get(8), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.OUTPUT, 113, 32, recipe.getOutput(), JEIDrawables.OUTPUT_SLOT, visible);
	}

	@Override
	public void draw(EnchanterRecipe recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			ALTAR.draw(poseStack, 15, 13);
			JEIDrawables.recipeArrow(recipe.getCraftingTime() * 50).draw(poseStack, 84, 32);
			TextRenderer font = font();
			Text timeComponent = getTimeComponent(recipe.getCraftingTime());
			font.draw(poseStack, timeComponent, 69, 71, 0x3F3F3F);
		}
	}
}
