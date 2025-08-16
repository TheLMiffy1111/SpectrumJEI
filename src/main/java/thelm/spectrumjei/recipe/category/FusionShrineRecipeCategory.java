package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.spectrum.recipe.fusion_shrine.FusionShrineRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.id.incubus_core.recipe.IngredientStack;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import thelm.jeidrawables.JEIDrawables;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on FusionShrineEmiRecipeGated
 */
public class FusionShrineRecipeCategory extends AbstractGatedRecipeCategory<FusionShrineRecipe> {

	public static final Text TITLE = Text.translatable("block.spectrum.fusion_shrine");

	public FusionShrineRecipeCategory() {
		super(SpectrumJEI.FUSION_SHRINE, TITLE);
	}

	@Override
	public int getHeight() {
		return 79;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, FusionShrineRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		if(recipe.getFluidInput() != Fluids.EMPTY) {
			addItem(builder, RecipeIngredientRole.CATALYST, 10, 26, new ItemStack(SpectrumBlocks.FUSION_SHRINE_BASALT), visible);
			addFluid(builder, RecipeIngredientRole.INPUT, 30, 26, recipe.getFluidInput(), fluidHelper().bucketVolume(), JEIDrawables.SLOT, visible);
		}
		else {
			addItem(builder, RecipeIngredientRole.CATALYST, 20, 26, new ItemStack(SpectrumBlocks.FUSION_SHRINE_BASALT), visible);
		}
		List<IngredientStack> ingredients = recipe.getIngredientStacks();
		int startX = 1 + getWidth() / 2 - ingredients.size() * 9;
		for(int i = 0; i < ingredients.size(); ++i) {
			addItem(builder, RecipeIngredientRole.INPUT, startX + i * 18, 1, ingredients.get(i).getStacks(), JEIDrawables.SLOT, visible);
		}
		addItem(builder, RecipeIngredientRole.OUTPUT, 94, 26, recipe.getOutput(), JEIDrawables.OUTPUT_SLOT, visible);
	}

	@Override
	public void draw(FusionShrineRecipe recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			JEIDrawables.recipeArrow(recipe.getCraftingTime() * 50).draw(poseStack, 57, 26);
			TextRenderer font = font();
			if(recipe.getDescription().isPresent()) {
				List<OrderedText> lines = font.wrapLines(recipe.getDescription().get(), 136);
				for(int i = 0; i < lines.size(); ++i) {
					font.draw(poseStack, lines.get(i), 0, 49 + i * 10, 0x3F3F3F);
				}
			}
			Text timeComponent = getTimeComponent(recipe.getCraftingTime(), recipe.getExperience());
			font.draw(poseStack, timeComponent, getWidth() / 2 - font.getWidth(timeComponent) / 2, 70, 0x3F3F3F);
		}
	}
}
