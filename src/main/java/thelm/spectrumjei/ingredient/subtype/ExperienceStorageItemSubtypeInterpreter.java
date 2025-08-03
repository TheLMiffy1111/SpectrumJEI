package thelm.spectrumjei.ingredient.subtype;

import java.util.List;

import de.dafuqs.spectrum.api.item.ExperienceStorageItem;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;

public class ExperienceStorageItemSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {

	@Override
	public Object getSubtypeData(ItemStack ingredient, UidContext context) {
		HolderLookup.Provider registries = Minecraft.getInstance().level.registryAccess();
		if(context == UidContext.Ingredient &&
				ingredient.getItem() instanceof ExperienceStorageItem xpStorageItem &&
				xpStorageItem.getMaxStoredExperience(registries, ingredient) > 0) {
			int capacity = xpStorageItem.getMaxStoredExperience(registries, ingredient);
			if(ExperienceStorageItem.getStoredExperience(ingredient) >= capacity) {
				return List.of(capacity, true);
			}
			return capacity;
		}
		return null;
	}

	@Override
	public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
		HolderLookup.Provider registries = Minecraft.getInstance().level.registryAccess();
		if(context == UidContext.Ingredient &&
				ingredient.getItem() instanceof ExperienceStorageItem xpStorageItem &&
				xpStorageItem.getMaxStoredExperience(registries, ingredient) > 0) {
			int capacity = xpStorageItem.getMaxStoredExperience(registries, ingredient);
			if(ExperienceStorageItem.getStoredExperience(ingredient) >= capacity) {
				return capacity + ",f";
			}
			return Integer.toString(capacity);
		}
		return "";
	}
}
