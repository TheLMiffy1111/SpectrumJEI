package thelm.fractaljei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class FractalJEI implements IModPlugin {

	public static final ResourceLocation UID = ResourceLocation.parse("fractaljei:fractal");

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addGuiContainerHandler(CreativeModeInventoryScreen.class, new SubTabExtraAreaHandler());
	}
}
