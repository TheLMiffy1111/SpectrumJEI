package thelm.spectrumjei.gui.render;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import thelm.spectrumjei.SpectrumJEI;

public record RecipeFlameDrawable(int duration) implements IDrawable {

	public static final RecipeFlameDrawable DEFAULT = new RecipeFlameDrawable(10000);

	public static final int WIDTH = 14;
	public static final int HEIGHT = 14;

	public static IDrawable of(int duration) {
		return duration == 0 ? SpectrumJEI.FLAME : new RecipeFlameDrawable(duration);
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
	public void draw(MatrixStack poseStack, int xOffset, int yOffset) {
		MinecraftClient minecraft = MinecraftClient.getInstance();
		int guiScale = minecraft.getWindow().calculateScaleFactor(minecraft.options.getGuiScale().getValue(), minecraft.forcesUnicodeFont());
		float mask = Math.round(System.currentTimeMillis() % duration * guiScale * HEIGHT / (float)duration) / (float)guiScale;
		SpectrumJEI.FLAME_EMPTY.draw(poseStack, xOffset, yOffset);
		SpectrumJEI.FLAME.draw(poseStack, xOffset, yOffset, mask, 0, 0, 0);
	}
}
