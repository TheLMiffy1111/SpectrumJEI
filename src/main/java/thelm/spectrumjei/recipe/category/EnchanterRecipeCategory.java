package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.items.magic_items.KnowledgeGemItem;
import de.dafuqs.spectrum.recipe.enchanter.EnchanterRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thelm.spectrumjei.SpectrumJEI;
import thelm.spectrumjei.gui.render.RecipeArrowDrawable;
import thelm.spectrumjei.gui.render.ResourceDrawable;

/**
 * Based on EnchanterEmiRecipeGated
 */
public class EnchanterRecipeCategory extends AbstractGatedRecipeCategory<EnchanterRecipe> {

	public static final Text TITLE = Text.translatable("container.spectrum.rei.enchanting.title");

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
		addItem(builder, RecipeIngredientRole.INPUT, 113, 7, KnowledgeGemItem.getKnowledgeDropStackWithXP(recipe.getRequiredExperience(), true), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.CATALYST, 113, 53, new ItemStack(SpectrumBlocks.ENCHANTER), visible);
		addItem(builder, RecipeIngredientRole.INPUT, 34, 32, ingredients.get(0), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 21, 1, ingredients.get(1), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 47, 1, ingredients.get(2), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 65, 19, ingredients.get(3), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 65, 45, ingredients.get(4), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 47, 63, ingredients.get(5), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 21, 63, ingredients.get(6), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 3, 45, ingredients.get(7), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 3, 19, ingredients.get(8), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.OUTPUT, 113, 32, recipe.getOutput(registryAccess()), SpectrumJEI.OUTPUT_SLOT, visible);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, EnchanterRecipe recipe, IFocusGroup focuses) {
		if(isVisible(recipe)) {
			builder.addDrawable(RecipeArrowDrawable.of(recipe.getCraftingTime() * 50), 84, 32);
		}
	}

	@Override
	public void draw(EnchanterRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipe)) {
			ALTAR.draw(guiGraphics, 15, 13);
			TextRenderer font = font();
			Text timeComponent = getTimeComponent(recipe.getCraftingTime());
			guiGraphics.drawText(font, timeComponent, 69, 70, 0x3F3F3F, false);
		}
	}
}
