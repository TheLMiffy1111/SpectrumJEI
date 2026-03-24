package thelm.spectrumjei.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import de.dafuqs.spectrum.items.MidnightAberrationItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

@Mixin(MidnightAberrationItem.class)
public abstract class MidnightAberrationItemMixin extends Item {

	private MidnightAberrationItemMixin(Item.Settings properties) {
		super(properties);
	}

	@Shadow
	public abstract ItemStack getStableStack();

	@Override
	public void appendStacks(ItemGroup tab, DefaultedList<ItemStack> items) {
		super.appendStacks(tab, items);
		if(isIn(tab)) {
			items.add(getStableStack());
		}
	}
}
