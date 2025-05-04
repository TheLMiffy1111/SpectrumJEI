package thelm.spectrumjei.gui.render;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public record ResourceDrawable(Identifier atlasLocation, int u, int v, int width, int height, int textureWidth, int textureHeight) implements IMaskableDrawable {

	public ResourceDrawable(Identifier atlasLocation, int u, int v, int width, int height) {
		this(atlasLocation, u, v, width, height, 256, 256);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void draw(MatrixStack poseStack, float xOffset, float yOffset, float maskTop, float maskBottom, float maskLeft, float maskRight) {
		GuiRenderUtil.blit(poseStack, atlasLocation, xOffset + maskLeft, yOffset + maskTop, u + maskLeft, v + maskTop, width - maskLeft - maskRight, height - maskTop - maskBottom, textureHeight, textureHeight);
	}
}
