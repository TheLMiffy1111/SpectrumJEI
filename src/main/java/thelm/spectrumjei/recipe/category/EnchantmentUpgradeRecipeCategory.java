package thelm.spectrumjei.recipe.category;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.blocks.enchanter.EnchanterBlockEntity;
import de.dafuqs.spectrum.items.magic_items.KnowledgeGemItem;
import de.dafuqs.spectrum.recipe.enchantment_upgrade.EnchantmentUpgradeRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thelm.jeidrawables.JEIDrawables;
import thelm.jeidrawables.gui.render.ResourceDrawable;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on EnchanterEmiRecipeGated and EnchantmentUpgradeEmiRecipeGated
 */
public class EnchantmentUpgradeRecipeCategory extends AbstractGatedRecipeCategory<EnchantmentUpgradeRecipe> {

	public static final Text TITLE = Text.translatable("container.spectrum.rei.enchantment_upgrading.title");

	public static final Identifier BACKGROUND = SpectrumCommon.locate("textures/gui/container/enchanter.png");
	public static final ResourceDrawable ALTAR = new ResourceDrawable(BACKGROUND, 0, 0, 54, 54);

	public EnchantmentUpgradeRecipeCategory() {
		super(SpectrumJEI.ENCHANTMENT_UPGRADE, TITLE);
	}

	@Override
	public int getHeight() {
		return 80;
	}

	@Override
	public boolean isUnlocked(EnchantmentUpgradeRecipe recipe) {
		return super.isUnlocked(recipe) && (!recipe.requiresUnlockedOverEnchanting() || hasAdvancement(EnchanterBlockEntity.OVERENCHANTING_ADVANCEMENT_IDENTIFIER));
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, EnchantmentUpgradeRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		Item inputItem = recipe.getRequiredItem();
		int inputCount = recipe.getRequiredItemCount();
		addItem(builder, RecipeIngredientRole.INPUT, 113, 7, KnowledgeGemItem.getKnowledgeDropStackWithXP(recipe.getRequiredExperience(), true), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.CATALYST, 113, 53, new ItemStack(SpectrumBlocks.ENCHANTER), visible);
		addItem(builder, RecipeIngredientRole.INPUT, 34, 32, recipe.getIngredients().get(0), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 21, 1, new ItemStack(inputItem, getSplitCount(inputCount, 0)), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 47, 1, new ItemStack(inputItem, getSplitCount(inputCount, 1)), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 65, 19, new ItemStack(inputItem, getSplitCount(inputCount, 2)), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 65, 45, new ItemStack(inputItem, getSplitCount(inputCount, 3)), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 47, 63, new ItemStack(inputItem, getSplitCount(inputCount, 4)), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 21, 63, new ItemStack(inputItem, getSplitCount(inputCount, 5)), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 3, 45, new ItemStack(inputItem, getSplitCount(inputCount, 6)), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.INPUT, 3, 19, new ItemStack(inputItem, getSplitCount(inputCount, 7)), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.OUTPUT, 113, 32, recipe.getOutput(), JEIDrawables.OUTPUT_SLOT, visible);
	}

	@Override
	public void draw(EnchantmentUpgradeRecipe recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		if(isVisible(recipe)) {
			ALTAR.draw(poseStack, 15, 13);
			JEIDrawables.RECIPE_ARROW.draw(poseStack, 84, 32);
			TextRenderer font = font();
			Text reqComponent = Text.translatable("container.spectrum.rei.enchantment_upgrade.required_item_count", recipe.getRequiredItemCount());
			font.draw(poseStack, reqComponent, 69, 71, 0x3F3F3F);
		}
	}

	public int getSplitCount(int inputCount, int index) {
		return inputCount / 8 + (index < inputCount % 8 ? 1 : 0);
	}
}
