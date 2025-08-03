package thelm.spectrumjei.ingredient.subtype;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import de.dafuqs.spectrum.energy.InkStorageItem;
import de.dafuqs.spectrum.energy.color.InkColor;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.item.ItemStack;

public class InkStorageItemSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {

	@Override
	public String apply(ItemStack ingredient, UidContext context) {
		if(context == UidContext.Ingredient && ingredient.getItem() instanceof InkStorageItem inkStorageItem) {
			Map<InkColor, Long> storage = inkStorageItem.getEnergyStorage(ingredient).getEnergy();
			return InkColor.all().stream().
					filter(color -> storage.getOrDefault(color, 0L) > 0).
					map(Objects::toString).
					collect(Collectors.joining(","));
		}
		return IIngredientSubtypeInterpreter.NONE;
	}
}
