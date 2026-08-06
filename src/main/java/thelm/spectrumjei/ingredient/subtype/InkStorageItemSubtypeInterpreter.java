package thelm.spectrumjei.ingredient.subtype;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import de.dafuqs.spectrum.api.ink.color.InkColor;
import de.dafuqs.spectrum.api.ink.storage.InkStorageItem;
import de.dafuqs.spectrum.registries.SpectrumRegistries;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;

public class InkStorageItemSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {

	@Override
	public Object getSubtypeData(ItemStack ingredient, UidContext context) {
		if(context == UidContext.Ingredient && ingredient.getItem() instanceof InkStorageItem inkStorageItem) {
			Map<InkColor, Long> storage = inkStorageItem.getEnergyStorage(ingredient).getEnergy();
			List<InkColor> colors = SpectrumRegistries.INK_COLOR.stream().
					filter(color -> storage.getOrDefault(color, 0L) > 0).
					toList();
			return colors.isEmpty() ? null : colors;
		}
		return "";
	}

	@Override
	public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
		if(context == UidContext.Ingredient && ingredient.getItem() instanceof InkStorageItem inkStorageItem) {
			Map<InkColor, Long> storage = inkStorageItem.getEnergyStorage(ingredient).getEnergy();
			return SpectrumRegistries.INK_COLOR.stream().
					filter(color -> storage.getOrDefault(color, 0L) > 0).
					map(Objects::toString).
					collect(Collectors.joining(","));
		}
		return "";
	}
}
