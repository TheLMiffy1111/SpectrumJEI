package thelm.spectrumjei.gui.handler;

import java.util.List;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.Rect2i;

public class OverlayHidingExtraAreaHandler<T extends HandledScreen<?>> implements IGuiContainerHandler<T> {

	@Override
	public List<Rect2i> getGuiExtraAreas(T containerScreen) {
		return List.of(new Rect2i(0, 0, containerScreen.width, containerScreen.height));
	}
}
