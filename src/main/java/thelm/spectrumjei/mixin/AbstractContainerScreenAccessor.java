package thelm.spectrumjei.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.ingame.HandledScreen;

@Mixin(HandledScreen.class)
public interface AbstractContainerScreenAccessor {

	@Accessor("x")
	int spectrumjei$guiLeft();

	@Accessor("y")
	int spectrumjei$guiTop();
}
