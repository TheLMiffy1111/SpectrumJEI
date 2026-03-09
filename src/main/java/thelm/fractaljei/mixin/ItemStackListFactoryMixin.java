package thelm.fractaljei.mixin;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import de.dafuqs.fractal.api.CreativeSubTab;
import mezz.jei.library.plugins.vanilla.ingredients.ItemStackListFactory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemStackListFactory.class)
public class ItemStackListFactoryMixin {

	@Shadow
	private static Logger LOGGER;

	@Inject(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTabs;allTabs()Ljava/util/List;"))
	private static void fractaljei$buildSubTabContents(CallbackInfoReturnable<List<ItemStack>> info, @Local CreativeModeTab.ItemDisplayParameters displayParameters) {
		for(CreativeSubTab tab : CreativeSubTab.SUBTABS) {
			if(tab.getType() != CreativeModeTab.Type.CATEGORY) {
				LOGGER.debug("Skipping creative tab: '{}' because it is type: {}", tab.getDisplayName().getString(), tab.getType());
				continue;
			}
			try {
				tab.buildContents(displayParameters);
			}
			catch (RuntimeException | LinkageError e) {
				LOGGER.error("Item Group crashed while building contents. Items from this group will be missing from the JEI ingredient list: {}", tab.getDisplayName().getString(), e);
				continue;
			}
		}
	}
}
