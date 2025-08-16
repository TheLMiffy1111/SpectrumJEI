package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.helpers.NullableDyeColor;
import de.dafuqs.spectrum.recipe.crystallarieum.CrystallarieumCatalyst;
import de.dafuqs.spectrum.recipe.crystallarieum.CrystallarieumRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.block.BlockState;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thelm.jeidrawables.JEIDrawables;
import thelm.jeidrawables.gui.render.ResourceDrawable;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on CrystallarieumEmiRecipeGated
 */
public class CrystallarieumRecipeCategory extends AbstractGatedRecipeCategory<CrystallarieumRecipe> {

	public static final Text TITLE = Text.translatable("block.spectrum.crystallarieum");

	public static final Text CATALYST = Text.translatable("container.spectrum.rei.crystallarieum.catalyst");
	public static final Text ACCELERATOR = Text.translatable("container.spectrum.rei.crystallarieum.accelerator");
	public static final Text INK_CONSUMPTION = Text.translatable("container.spectrum.rei.crystallarieum.ink_consumption");
	public static final Text USED_UP = Text.translatable("container.spectrum.rei.crystallarieum.used_up");

	public static final Identifier BACKGROUND = SpectrumCommon.locate("textures/gui/modonomicon/crystallarieum.png");
	public static final ResourceDrawable ACCEL_HIGHER = new ResourceDrawable(BACKGROUND, 85, 0, 6, 6, 128, 128);
	public static final ResourceDrawable ACCEL_HIGH = new ResourceDrawable(BACKGROUND, 67, 0, 6, 6, 128, 128);
	public static final ResourceDrawable ACCEL_NONE = new ResourceDrawable(BACKGROUND, 97, 0, 6, 6, 128, 128);
	public static final ResourceDrawable ACCEL_LOW = new ResourceDrawable(BACKGROUND, 73, 0, 6, 6, 128, 128);
	public static final ResourceDrawable ACCEL_LOWER = new ResourceDrawable(BACKGROUND, 79, 0, 6, 6, 128, 128);
	public static final ResourceDrawable CONSUME_HIGHER = new ResourceDrawable(BACKGROUND, 85, 6, 6, 6, 128, 128);
	public static final ResourceDrawable CONSUME_HIGH = new ResourceDrawable(BACKGROUND, 67, 6, 6, 6, 128, 128);
	public static final ResourceDrawable CONSUME_NORMAL = new ResourceDrawable(BACKGROUND, 91, 6, 6, 6, 128, 128);
	public static final ResourceDrawable CONSUME_NONE = new ResourceDrawable(BACKGROUND, 97, 6, 6, 6, 128, 128);
	public static final ResourceDrawable CONSUME_LOW = new ResourceDrawable(BACKGROUND, 73, 6, 6, 6, 128, 128);
	public static final ResourceDrawable CONSUME_LOWER = new ResourceDrawable(BACKGROUND, 79, 6, 6, 6, 128, 128);

	public CrystallarieumRecipeCategory() {
		super(SpectrumJEI.CRYSTALLARIEUM, TITLE);
	}

	@Override
	public int getHeight() {
		return 99;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, CrystallarieumRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		addItem(builder, RecipeIngredientRole.INPUT, 7, 9, recipe.getIngredientStack(), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.CATALYST, 27, 19, SpectrumBlocks.CRYSTALLARIEUM.asStackWithColor(NullableDyeColor.get(recipe.getInkColor().getDyeColor())), visible);
		List<ItemStack> growthStages = recipe.getGrowthStages().stream().map(BlockState::getBlock).map(ItemStack::new).toList();
		addItem(builder, RecipeIngredientRole.INPUT, 27, 1, growthStages.get(0), JEIDrawables.SLOT, visible);
		for(int i = 1; i < growthStages.size(); ++i) {
			addItem(builder, RecipeIngredientRole.OUTPUT, 53 + i * 20, 9, growthStages.get(i), JEIDrawables.SLOT, visible);
		}
		List<CrystallarieumCatalyst> catalysts = recipe.getCatalysts();
		for(int i = 0; i < catalysts.size(); ++i) {
			int x = 53 + i * 18;
			addItem(builder, RecipeIngredientRole.CATALYST, x, 39, catalysts.get(i).ingredient, JEIDrawables.SLOT, visible);
		}
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, CrystallarieumRecipe recipe, IFocusGroup focuses) {
		if(isVisible(recipe)) {
			builder.addDrawable(JEIDrawables.recipeArrow(recipe.getSecondsPerGrowthStage() * 1000), 47, 9);
			List<CrystallarieumCatalyst> catalysts = recipe.getCatalysts();
			for(int i = 0; i < catalysts.size(); ++i) {
				CrystallarieumCatalyst catalyst = catalysts.get(i);
				int x = 58 + i * 18;
				IDrawable icon;

				float growthAcceleration = catalyst.growthAccelerationMod;
				if(growthAcceleration >= 6F) {
					icon = ACCEL_HIGHER;
				}
				else if(growthAcceleration > 1F) {
					icon = ACCEL_HIGH;
				}
				else if(growthAcceleration == 1F) {
					icon = ACCEL_NONE;
				}
				else if(growthAcceleration > 0.25F) {
					icon = ACCEL_LOW;
				}
				else {
					icon = ACCEL_LOWER;
				}
				builder.addDrawable(icon, x, 59);

				float inkConsumption = catalyst.inkConsumptionMod;
				if(inkConsumption >= 8F) {
					icon = CONSUME_HIGHER;
				}
				else if(inkConsumption > 1F) {
					icon = CONSUME_HIGH;
				}
				else if(inkConsumption == 1F) {
					icon = CONSUME_NONE;
				}
				else if(inkConsumption > 0.25F) {
					icon = CONSUME_LOW;
				}
				else {
					icon = CONSUME_LOWER;
				}
				builder.addDrawable(icon, x, 69);

				float consumeChance = catalyst.consumeChancePerSecond;
				if(consumeChance >= 0.2F) {
					icon = CONSUME_HIGHER;
				}
				else if(consumeChance >= 0.05F) {
					icon = CONSUME_HIGH;
				}
				else if(consumeChance > 0F) {
					icon = CONSUME_NORMAL;
				}
				else {
					icon = CONSUME_NONE;
				}
				builder.addDrawable(icon, x, 79);
			}
		}
	}

	@Override
	public void draw(CrystallarieumRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipe)) {
			TextRenderer font = font();
			Text timeComponent;
			if(recipe.growsWithoutCatalyst()) {
				timeComponent = Text.translatable("container.spectrum.rei.crystallarieum.crafting_time_per_stage_seconds_catalyst_optional", recipe.getSecondsPerGrowthStage());
			}
			else {
				timeComponent = Text.translatable("container.spectrum.rei.crystallarieum.crafting_time_per_stage_seconds", recipe.getSecondsPerGrowthStage());
			}
			guiGraphics.drawText(font, CATALYST, 6, 43, 0x3F3F3F, false);
			guiGraphics.drawText(font, ACCELERATOR, 6, 58, 0x3F3F3F, false);
			guiGraphics.drawText(font, INK_CONSUMPTION, 6, 68, 0x3F3F3F, false);
			guiGraphics.drawText(font, USED_UP, 6, 78, 0x3F3F3F, false);
			guiGraphics.drawText(font, timeComponent, getWidth() / 2 - font.getWidth(timeComponent) / 2, 90, 0x3F3F3F, false);
		}
	}
}
