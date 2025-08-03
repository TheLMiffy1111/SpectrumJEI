package thelm.spectrumjei.ingredient.subtype;

import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;

public class UnstableItemSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {

	@Override
	public Object getSubtypeData(ItemStack ingredient, UidContext context) {
		if(context == UidContext.Ingredient && ingredient.has(SpectrumDataComponentTypes.STABLE)) {
			return true;
		}
		return null;
	}

	@Override
	public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
		return getSubtypeData(ingredient, context) == null ? "" : "s";
	}
}
