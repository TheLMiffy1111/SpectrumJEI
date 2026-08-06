package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.recipe.crystallarieum.CrystallarieumAdditive;
import de.dafuqs.spectrum.recipe.crystallarieum.CrystallarieumRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import thelm.jeidrawables.JEIDrawables;
import thelm.jeidrawables.gui.render.ResourceDrawable;
import thelm.spectrumjei.SpectrumJEI;

/**
 * Based on CrystallarieumEmiRecipeGated
 */
public class CrystallarieumRecipeCategory extends AbstractGatedRecipeCategory<CrystallarieumRecipe> {

	public static final Component TITLE = Component.translatable("block.spectrum.crystallarieum");

	public static final Component CATALYST = Component.translatable("container.spectrum.rei.crystallarieum.additive");
	public static final Component SPEED = Component.translatable("container.spectrum.rei.crystallarieum.speed");
	public static final Component INK_DRAIN = Component.translatable("container.spectrum.rei.crystallarieum.ink_drain");
	public static final Component DEPLETION = Component.translatable("container.spectrum.rei.crystallarieum.depletion");

	public static final ResourceLocation BACKGROUND = SpectrumCommon.locate("textures/gui/modonomicon/crystallarieum.png");
	public static final Int2ObjectMap<ResourceDrawable> GROWTH_SPEED = new Int2ObjectArrayMap<>(5);
	public static final Int2ObjectMap<ResourceDrawable> CONSUMPTION = new Int2ObjectArrayMap<>(5);
	public static final Int2ObjectMap<ResourceDrawable> CONSUME_CHANCE = new Int2ObjectArrayMap<>(5);

	public CrystallarieumRecipeCategory() {
		super(SpectrumJEI.CRYSTALLARIEUM, TITLE);
	}

	@Override
	public int getHeight() {
		return 99;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CrystallarieumRecipe> recipeHolder, IFocusGroup focuses) {
		boolean visible = isVisible(recipeHolder);
		CrystallarieumRecipe recipe = recipeHolder.value();
		addItem(builder, RecipeIngredientRole.INPUT, 7, 9, recipe.getIngredientStack(), JEIDrawables.SLOT, visible);
		ItemStack stack = SpectrumBlocks.CRYSTALLARIEUM.toStack();
		stack.set(SpectrumDataComponentTypes.INK_COLOR, recipe.getInkColor());
		addItem(builder, RecipeIngredientRole.CATALYST, 27, 19, stack, visible);
		List<ItemStack> growthStages = recipe.getGrowthStages().stream().map(BlockState::getBlock).map(ItemStack::new).toList();
		addItem(builder, RecipeIngredientRole.INPUT, 27, 1, growthStages.get(0), JEIDrawables.SLOT, visible);
		for(int i = 1; i < growthStages.size(); ++i) {
			addItem(builder, RecipeIngredientRole.OUTPUT, 53 + i * 20, 9, growthStages.get(i), JEIDrawables.SLOT, visible);
		}
		List<CrystallarieumAdditive> additives = recipe.getAdditives();
		for(int i = 0; i < additives.size(); ++i) {
			int x = 53 + i * 18;
			addItem(builder, RecipeIngredientRole.CATALYST, x, 39, additives.get(i).ingredient(), JEIDrawables.SLOT, visible);
		}
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<CrystallarieumRecipe> recipeHolder, IFocusGroup focuses) {
		if(isVisible(recipeHolder)) {
			CrystallarieumRecipe recipe = recipeHolder.value();
			builder.addDrawable(JEIDrawables.recipeArrow(recipe.getSecondsPerGrowthStage() * 1000), 47, 9);
			List<CrystallarieumAdditive> additives = recipe.getAdditives();
			for(int i = 0; i < additives.size(); ++i) {
				CrystallarieumAdditive additive = additives.get(i);
				int x = 58 + i * 18;
				int offsetU;
				IDrawable icon;

				offsetU = CrystallarieumRecipe.growthSpeedOffsetU(additive);
				icon = GROWTH_SPEED.computeIfAbsent(offsetU, u -> new ResourceDrawable(BACKGROUND, u, CrystallarieumRecipe.GROWTH_SPEED_V, 7, 7, 128, 128));
				builder.addDrawable(icon, x, 59);

				offsetU = CrystallarieumRecipe.consumptionOffsetU(additive, offsetU);
				icon = CONSUMPTION.computeIfAbsent(offsetU, u -> new ResourceDrawable(BACKGROUND, u, CrystallarieumRecipe.CONSUMPTION_V, 7, 7, 128, 128));
				builder.addDrawable(icon, x, 69);

				offsetU = CrystallarieumRecipe.consumeChanceOffsetU(additive, offsetU);
				icon = CONSUME_CHANCE.computeIfAbsent(offsetU, u -> new ResourceDrawable(BACKGROUND, u, CrystallarieumRecipe.CONSUME_CHANCE_V, 7, 7, 128, 128));
				builder.addDrawable(icon, x, 79);
			}
		}
	}

	@Override
	public void draw(RecipeHolder<CrystallarieumRecipe> recipeHolder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipeHolder, recipeSlotsView, guiGraphics, mouseX, mouseY);
		if(isVisible(recipeHolder)) {
			CrystallarieumRecipe recipe = recipeHolder.value();
			Font font = font();
			Component timeComponent;
			if(recipe.growsWithoutAdditive()) {
				timeComponent = Component.translatable("container.spectrum.rei.crystallarieum.crafting_time_per_stage_seconds_additive_optional", recipe.getSecondsPerGrowthStage());
			}
			else {
				timeComponent = Component.translatable("container.spectrum.rei.crystallarieum.crafting_time_per_stage_seconds", recipe.getSecondsPerGrowthStage());
			}
			guiGraphics.drawString(font, CATALYST, 6, 43, 0x3F3F3F, false);
			guiGraphics.drawString(font, SPEED, 6, 58, 0x3F3F3F, false);
			guiGraphics.drawString(font, INK_DRAIN, 6, 68, 0x3F3F3F, false);
			guiGraphics.drawString(font, DEPLETION, 6, 78, 0x3F3F3F, false);
			guiGraphics.drawString(font, timeComponent, getWidth() / 2 - font.width(timeComponent) / 2, 90, 0x3F3F3F, false);
		}
	}
}
