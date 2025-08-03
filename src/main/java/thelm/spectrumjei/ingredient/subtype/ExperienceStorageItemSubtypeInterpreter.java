package thelm.spectrumjei.ingredient.subtype;

import de.dafuqs.spectrum.items.ExperienceStorageItem;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.item.ItemStack;

public class ExperienceStorageItemSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {

	@Override
	public String apply(ItemStack ingredient, UidContext context) {
		if(context == UidContext.Ingredient &&
				ingredient.getItem() instanceof ExperienceStorageItem xpStorageItem &&
				xpStorageItem.getMaxStoredExperience(ingredient) > 0) {
			int capacity = xpStorageItem.getMaxStoredExperience(ingredient);
			if(ExperienceStorageItem.getStoredExperience(ingredient) >= capacity) {
				return capacity + ",f";
			}
			return Integer.toString(capacity);
		}
		return IIngredientSubtypeInterpreter.NONE;
	}
}
