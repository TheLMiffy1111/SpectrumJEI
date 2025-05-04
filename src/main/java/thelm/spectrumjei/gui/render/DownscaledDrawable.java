package thelm.spectrumjei.gui.render;

import com.google.common.base.Preconditions;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.util.math.MatrixStack;

/**
 * Clone of HighResolutionDrawable required to recreate the shapeless icon
 */
public record DownscaledDrawable(IDrawable drawable, int scale) implements IDrawable {

	public DownscaledDrawable(IDrawable drawable, int scale) {
		int width = drawable.getWidth();
		int height = drawable.getHeight();
		Preconditions.checkArgument(
				width % scale == 0,
				String.format("drawable width %s must be divisible by the scale %s", width, scale));
		Preconditions.checkArgument(
				height % scale == 0,
				String.format("drawable height %s must be divisible by the scale %s", height, scale));
		this.drawable = drawable;
		this.scale = scale;
	}

	@Override
	public int getWidth() {
		return drawable.getWidth() / scale;
	}

	@Override
	public int getHeight() {
		return drawable.getHeight() / scale;
	}

	@Override
	public void draw(MatrixStack poseStack, int xOffset, int yOffset) {
		poseStack.push();
		poseStack.translate(xOffset, yOffset, 0);
		poseStack.scale(1F / scale, 1F / scale, 1);
		drawable.draw(poseStack);
		poseStack.pop();
	}
}
