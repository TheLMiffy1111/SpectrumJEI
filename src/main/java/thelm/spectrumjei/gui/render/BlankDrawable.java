package thelm.spectrumjei.gui.render;

import net.minecraft.client.gui.DrawContext;

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
	public void draw(DrawContext guiGraphics, float xOffset, float yOffset, float maskTop, float maskBottom, float maskLeft, float maskRight) {}
}
