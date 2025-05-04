package thelm.spectrumjei.gui.render;

import net.minecraft.client.util.math.MatrixStack;

public record BlankDrawable(int width, int height) implements IMaskableDrawable {

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void draw(MatrixStack poseStack, float xOffset, float yOffset, float maskTop, float maskBottom, float maskLeft, float maskRight) {}
}
