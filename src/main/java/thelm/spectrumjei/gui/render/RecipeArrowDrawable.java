package thelm.spectrumjei.gui.render;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import thelm.spectrumjei.SpectrumJEI;

public record RecipeArrowDrawable(int duration) implements IDrawable {

	public static final int WIDTH = 22;
	public static final int HEIGHT = 16;

	public static IDrawable of(int duration) {
		return duration == 0 ? SpectrumJEI.RECIPE_ARROW : new RecipeArrowDrawable(duration);
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public void draw(DrawContext guiGraphics, int xOffset, int yOffset) {
		MinecraftClient minecraft = MinecraftClient.getInstance();
		int guiScale = minecraft.getWindow().calculateScaleFactor(minecraft.options.getGuiScale().getValue(), minecraft.forcesUnicodeFont());
		float mask = WIDTH - Math.round(System.currentTimeMillis() % duration * guiScale * WIDTH / (float)duration) / (float)guiScale;
		SpectrumJEI.RECIPE_ARROW.draw(guiGraphics, xOffset, yOffset);
		SpectrumJEI.RECIPE_ARROW_FILLED.draw(guiGraphics, xOffset, yOffset, 0, 0, 0, mask);
	}
}
