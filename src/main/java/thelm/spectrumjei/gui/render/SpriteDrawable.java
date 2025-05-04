package thelm.spectrumjei.gui.render;

import java.util.function.Supplier;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;

public record SpriteDrawable(Supplier<Sprite> spriteSupplier, int u, int v, int width, int height, int textureWidth, int textureHeight) implements IMaskableDrawable {

	public SpriteDrawable(Supplier<Sprite> spriteSupplier, int textureWidth, int textureHeight) {
		this(spriteSupplier, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
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
		GuiRenderUtil.blitSprite(poseStack, spriteSupplier.get(), xOffset + maskLeft, yOffset + maskTop, u + maskLeft, v + maskTop, width - maskLeft - maskRight, height - maskTop - maskBottom, textureWidth, textureHeight);
	}
}
