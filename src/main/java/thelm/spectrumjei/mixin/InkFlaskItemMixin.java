package thelm.spectrumjei.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import de.dafuqs.spectrum.energy.InkStorageItem;
import de.dafuqs.spectrum.energy.color.InkColor;
import de.dafuqs.spectrum.energy.storage.SingleInkStorage;
import de.dafuqs.spectrum.items.energy.InkFlaskItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

@Mixin(InkFlaskItem.class)
public abstract class InkFlaskItemMixin extends Item implements InkStorageItem<SingleInkStorage> {

	private InkFlaskItemMixin(Item.Settings properties) {
		super(properties);
	}

	@Shadow
	public abstract ItemStack getFullStack(InkColor color);

	@Override
	public void appendStacks(ItemGroup tab, DefaultedList<ItemStack> items) {
		super.appendStacks(tab, items);
		if(tab == ItemGroup.SEARCH) {
			for(InkColor color : InkColor.all()) {
				items.add(getFullStack(color));
			}
		}
	}
}
