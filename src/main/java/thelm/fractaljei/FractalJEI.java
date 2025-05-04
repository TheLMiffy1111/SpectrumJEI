package thelm.fractaljei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.util.Identifier;

public class FractalJEI implements IModPlugin {

	public static final Identifier UID = new Identifier("fractaljei:fractal");

	@Override
	public Identifier getPluginUid() {
		return UID;
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addGuiContainerHandler(CreativeInventoryScreen.class, new SubTabExtraAreaHandler());
	}
}
