package thelm.spectrumjei.gui.render;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class GuiRenderUtil {

	public static void blit(MatrixStack poseStack, Identifier atlasLocation, float x, float y, float uOffset, float vOffset, float width, float height, int textureWidth, int textureHeight) {
		float uMin = uOffset / textureWidth;
		float uMax = (uOffset + width) / textureWidth;
		float vMin = vOffset / textureHeight;
		float vMax = (vOffset + height) / textureHeight;
		blit(poseStack, atlasLocation, x, x + width, y, y + height, uMin, uMax, vMin, vMax);
	}

	public static void blitSprite(MatrixStack poseStack, Sprite sprite, float x, float y, float uOffset, float vOffset, float width, float height, int textureWidth, int textureHeight) {
		float spriteWidth = sprite.getMaxU() - sprite.getMinU();
		float spriteHeight = sprite.getMaxV() - sprite.getMinV();
		float uMin = sprite.getMinU() + uOffset / textureWidth * spriteWidth;
		float uMax = sprite.getMinU() + (uOffset + width) / textureWidth * spriteWidth;
		float vMin = sprite.getMinV() + vOffset / textureHeight * spriteHeight;
		float vMax = sprite.getMinV() + (vOffset + height) / textureHeight * spriteHeight;
		blit(poseStack, sprite.getAtlas().getId(), x, x + width, y, y + height, uMin, uMax, vMin, vMax);
	}

	static void blit(MatrixStack poseStack, Identifier atlasLocation, float xMin, float xMax, float yMin, float yMax, float uMin, float uMax, float vMin, float vMax) {
		RenderSystem.setShaderTexture(0, atlasLocation);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		Matrix4f matrix = poseStack.peek().getPositionMatrix();
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(matrix, xMin, yMin, 0).texture(uMin, vMin).next();
		bufferBuilder.vertex(matrix, xMin, yMax, 0).texture(uMin, vMax).next();
		bufferBuilder.vertex(matrix, xMax, yMax, 0).texture(uMax, vMax).next();
		bufferBuilder.vertex(matrix, xMax, yMin, 0).texture(uMax, vMin).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);
	}
}
