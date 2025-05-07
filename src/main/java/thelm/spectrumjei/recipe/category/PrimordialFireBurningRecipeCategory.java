package thelm.spectrumjei.recipe.category;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thelm.jeidrawables.JEIDrawables;
import thelm.jeidrawables.gui.render.SpriteDrawable;
import thelm.spectrumjei.SpectrumJEI;

public class PrimordialFireBurningRecipeCategory extends AbstractGatedRecipeCategory<PrimordialFireBurningRecipe> {

	public static final Text TITLE = Text.translatable("container.spectrum.rei.primordial_fire_burning.title");

	public static final Identifier FIRE_TEXTURE = SpectrumCommon.locate("block/primordial_fire_0");
	public static final SpriteDrawable FIRE = new SpriteDrawable(() -> {
		return MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(FIRE_TEXTURE);
	}, 16, 16);

	public PrimordialFireBurningRecipeCategory() {
		super(SpectrumJEI.PRIMORDIAL_FIRE_BURNING, TITLE);
	}

	@Override
	public int getHeight() {
		return 35;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, PrimordialFireBurningRecipe recipe, IFocusGroup focuses) {
		boolean visible = isVisible(recipe);
		addItem(builder, RecipeIngredientRole.INPUT, 29, 1, recipe.getIngredients().get(0), JEIDrawables.SLOT, visible);
		addItem(builder, RecipeIngredientRole.OUTPUT, 87, 9, recipe.getOutput(registryAccess()), JEIDrawables.OUTPUT_SLOT, visible);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, PrimordialFireBurningRecipe recipe, IFocusGroup focuses) {
		if(isVisible(recipe)) {
			builder.addDrawable(FIRE, 29, 19);
			builder.addDrawable(JEIDrawables.RECIPE_ARROW, 53, 9);
		}
	}
}
