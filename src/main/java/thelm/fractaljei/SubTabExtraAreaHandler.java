package thelm.fractaljei;

import java.util.List;

import de.dafuqs.fractal.interfaces.ICreativeTabParent;
import de.dafuqs.fractal.interfaces.ISubTabLocation;
import de.dafuqs.fractal.mixin.client.CreativeModeInventoryScreenAccessor;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.item.CreativeModeTab;

public class SubTabExtraAreaHandler implements IGuiContainerHandler<CreativeModeInventoryScreen> {

	@Override
	public List<Rect2i> getGuiExtraAreas(CreativeModeInventoryScreen containerScreen) {
		CreativeModeTab selected = CreativeModeInventoryScreenAccessor.fractal$getSelectedTab();
		if(selected instanceof ICreativeTabParent parent &&
				containerScreen instanceof ISubTabLocation stl &&
				parent.fractal$getChildren() != null &&
				!parent.fractal$getChildren().isEmpty()) {
			return List.of(
					new Rect2i(stl.fractal$getX(), stl.fractal$getY(), 72, stl.fractal$getH()),
					new Rect2i(stl.fractal$getX2(), stl.fractal$getY(), 72, stl.fractal$getH2()));
		}
		return List.of();
	}
}
