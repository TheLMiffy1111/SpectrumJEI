package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.spectrum.helpers.LoreHelper;
import de.dafuqs.spectrum.recipe.spirit_instiller.SpiritInstillerRecipe;
import de.dafuqs.spectrum.recipe.spirit_instiller.spawner.SpawnerChangeRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.id.incubus_core.recipe.IngredientStack;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import thelm.spectrumjei.SpectrumJEI;
import thelm.spectrumjei.gui.render.RecipeArrowDrawable;

/**
 * Based on SpiritInstillingEmiRecipeGated
 */
public class SpiritInstillerRecipeCategory extends AbstractGatedRecipeCategory<SpiritInstillerRecipe> {

	public static final Text TITLE = Text.translatable("block.spectrum.spirit_instiller");

	public SpiritInstillerRecipeCategory() {
		super(SpectrumJEI.SPIRIT_INSTILLER, TITLE);
	}

	@Override
	public int getHeight() {
		return 48;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, SpiritInstillerRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		List<IngredientStack> ingredients = recipe.getIngredientStacks();
		addItem(builder, RecipeIngredientRole.INPUT, 31, 1, ingredients.get(0).getStacks(), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 11, 1, ingredients.get(1).getStacks(), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 51, 1, ingredients.get(2).getStacks(), SpectrumJEI.SLOT, visible);
		addItem(builder, RecipeIngredientRole.CATALYST, 31, 18, new ItemStack(SpectrumBlocks.SPIRIT_INSTILLER), visible);
		addItem(builder, RecipeIngredientRole.CATALYST, 11, 18, new ItemStack(SpectrumBlocks.ITEM_BOWL_CALCITE), visible);
		addItem(builder, RecipeIngredientRole.CATALYST, 51, 18, new ItemStack(SpectrumBlocks.ITEM_BOWL_CALCITE), visible);
		ItemStack stack = recipe.getOutput();
		if(recipe instanceof SpawnerChangeRecipe spawnerChange) {
			LoreHelper.setLore(stack, spawnerChange.getOutputLoreText());
		}
		addItem(builder, RecipeIngredientRole.OUTPUT, 105, 10, stack, SpectrumJEI.OUTPUT_SLOT, visible);
	}

	@Override
	public void draw(SpiritInstillerRecipe recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			RecipeArrowDrawable.of(recipe.getCraftingTime() * 50).draw(poseStack, 73, 10);
			TextRenderer font = font();
			Text timeComponent = getTimeComponent(recipe.getCraftingTime(), recipe.getExperience());
			font.draw(poseStack, timeComponent, getWidth() / 2 - font.getWidth(timeComponent) / 2, 38, 0x3F3F3F);
		}
	}
}
