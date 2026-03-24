package thelm.spectrumjei.ingredient.subtype;

import de.dafuqs.spectrum.blocks.memory.MemoryItem;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;

public class MemoryItemSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {

	@Override
	public String apply(ItemStack ingredient, UidContext context) {
		if(ingredient.getItem() instanceof MemoryItem) {
			return MemoryItem.getEntityType(ingredient.getNbt()).
					map(EntityType::getId).
					map(Object::toString).
					orElse(NONE);
		}
		return NONE;
	}
}
