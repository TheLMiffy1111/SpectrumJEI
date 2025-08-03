package thelm.spectrumjei.ingredient.subtype;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.item.ItemStack;

public class UnstableItemSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {

	@Override
	public String apply(ItemStack ingredient, UidContext context) {
		if(context == UidContext.Ingredient && ingredient.hasNbt() && ingredient.getNbt().getBoolean("Stable")) {
			return "s";
		}
		return IIngredientSubtypeInterpreter.NONE;
	}
}
