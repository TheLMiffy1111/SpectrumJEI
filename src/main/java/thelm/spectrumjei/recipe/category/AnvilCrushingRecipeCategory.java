package thelm.spectrumjei.recipe.category;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.recipe.anvil_crushing.AnvilCrushingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thelm.jeidrawables.JEIDrawables;
import thelm.jeidrawables.gui.render.ResourceDrawable;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on AnvilCrushingEmiRecipeGated
 */
public class AnvilCrushingRecipeCategory extends AbstractGatedRecipeCategory<AnvilCrushingRecipe> {

	public static final Text TITLE = Text.translatable("container.spectrum.rei.anvil_crushing.title");

	public static final Text LOW_FORCE = Text.translatable("container.spectrum.rei.anvil_crushing.low_force_required");
	public static final Text MEDIUM_FORCE = Text.translatable("container.spectrum.rei.anvil_crushing.medium_force_required");
	public static final Text HIGH_FORCE = Text.translatable("container.spectrum.rei.anvil_crushing.high_force_required");

	public static final Identifier BACKGROUND = SpectrumCommon.locate("textures/gui/container/anvil_crushing.png");
	public static final ResourceDrawable WALL = new ResourceDrawable(BACKGROUND, 0, 0, 16, 48);
	public static final ResourceDrawable FALL = new ResourceDrawable(BACKGROUND, 16, 1, 16, 16);

	public AnvilCrushingRecipeCategory() {
		super(SpectrumJEI.ANVIL_CRUSHING, TITLE);
	}

	@Override
	public int getHeight() {
		return 64;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, AnvilCrushingRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		addItem(builder, RecipeIngredientRole.INPUT, 32, 31, recipe.getIngredients().get(0), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.CATALYST, 32, 11, new ItemStack(Items.ANVIL), visible);
		addItem(builder, RecipeIngredientRole.OUTPUT, 105, 16, recipe.getOutput(), JEIDrawables.OUTPUT_SLOT, visible);
	}

	@Override
	public void draw(AnvilCrushingRecipe recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			WALL.draw(poseStack, 10, 1);
			FALL.draw(poseStack, 32, 1);
			JEIDrawables.RECIPE_ARROW.draw(poseStack, 60, 16);
			TextRenderer font = font();
			Text xpComponent = Text.translatable("container.spectrum.rei.anvil_crushing.plus_xp", recipe.getExperience());
			Text forceComponent = getForceComponent(recipe);
			font.draw(poseStack, xpComponent, 126 - font.getWidth(xpComponent), 40, 0x3F3F3F);
			font.draw(poseStack, forceComponent, getWidth() / 2 - font.getWidth(forceComponent) / 2, 54, 0x3F3F3F);
		}
	}

	public Text getForceComponent(AnvilCrushingRecipe recipe) {
		if(recipe.getCrushedItemsPerPointOfDamage() >= 1F) {
			return LOW_FORCE;
		}
		else if(recipe.getCrushedItemsPerPointOfDamage() >= 0.5F) {
			return MEDIUM_FORCE;
		}
		else {
			return HIGH_FORCE;
		}
	}
}
