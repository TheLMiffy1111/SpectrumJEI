package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.matchbooks.recipe.IngredientStack;
import de.dafuqs.spectrum.api.recipe.FluidIngredient;
import de.dafuqs.spectrum.recipe.titration_barrel.ITitrationBarrelRecipe;
import de.dafuqs.spectrum.recipe.titration_barrel.TitrationBarrelRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import thelm.jeidrawables.JEIDrawables;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on TitrationBarrelEmiRecipeGated
 */
public class TitrationBarrelRecipeCategory extends AbstractGatedRecipeCategory<ITitrationBarrelRecipe> {

	public static final Text TITLE = Text.translatable("block.spectrum.titration_barrel");

	public TitrationBarrelRecipeCategory() {
		super(SpectrumJEI.TITRATION_BARREL, TITLE);
	}

	@Override
	public int getHeight() {
		return 50;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, ITitrationBarrelRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		List<IngredientStack> ingredients = recipe.getIngredientStacks();
		int inputCount = ingredients.size();
		boolean hasFluid = false;
		if(recipe.getFluidInput() != FluidIngredient.EMPTY) {
			inputCount++;
			hasFluid = true;
		}
		int startX = Math.max(11, 41 - inputCount * 10);
		int startY = inputCount > 3 ? 1 : 11;
		if(hasFluid) {
			addFluid(builder, RecipeIngredientRole.INPUT, startX, startY, recipe.getFluidInput(), fluidHelper().bucketVolume(), JEIDrawables.SLOT, visible);
		}
		for(int i = 0; i < ingredients.size(); ++i) {
			int x = startX + (hasFluid ? i + 1 : i) % 3 * 20;
			int y = startY + (hasFluid ? i + 1 : i) / 3 * 20;
			addItem(builder, RecipeIngredientRole.INPUT, x, y, ingredients.get(i).getStacks(), JEIDrawables.SLOT, visible);
		}
		if(recipe.getTappingItem() != null && recipe.getTappingItem() != Items.AIR) {
			addItem(builder, RecipeIngredientRole.INPUT, 76, 21, new ItemStack(recipe.getTappingItem()), JEIDrawables.SLOT, visible);
		}
		List<ItemStack> outputVariations;
		if(recipe instanceof TitrationBarrelRecipe titrationBarrelRecipe && titrationBarrelRecipe.getFermentationData() != null) {
			outputVariations = List.copyOf(titrationBarrelRecipe.getOutputVariations(TitrationBarrelRecipe.FERMENTATION_DURATION_DISPLAY_TIME_MULTIPLIERS));
		}
		else {
			outputVariations = List.of(recipe.getOutput(registryAccess()));
		}
		addItem(builder, RecipeIngredientRole.OUTPUT, 105, 10, outputVariations, JEIDrawables.OUTPUT_SLOT, visible);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, ITitrationBarrelRecipe recipe, IFocusGroup focuses) {
		if(isVisible(recipe)) {
			IDrawable recipeArrow = JEIDrawables.recipeArrow(recipe.getMinFermentationTimeHours() * 1000);
			if(recipe.getTappingItem() == null || recipe.getTappingItem() == Items.AIR) {
				builder.addDrawable(recipeArrow, 73, 10);
			}
			else {
				builder.addDrawable(recipeArrow, 73, 2);
			}
		}
	}

	@Override
	public void draw(ITitrationBarrelRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipe)) {
			TextRenderer font = font();
			Text durationComponent = TitrationBarrelRecipe.getDurationText(recipe.getMinFermentationTimeHours(), recipe.getFermentationData());
			guiGraphics.drawText(font, durationComponent, getWidth() / 2 - font.getWidth(durationComponent) / 2, 40, 0x3F3F3F, false);
		}
	}

}
