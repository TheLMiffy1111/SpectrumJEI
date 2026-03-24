package thelm.spectrumjei.mixin;

import org.spongepowered.asm.mixin.Mixin;

import de.dafuqs.spectrum.energy.InkStorage;
import de.dafuqs.spectrum.energy.InkStorageItem;
import de.dafuqs.spectrum.items.energy.ArtistsPaletteItem;
import de.dafuqs.spectrum.items.energy.InkAssortmentItem;
import de.dafuqs.spectrum.items.energy.PigmentPaletteItem;
import de.dafuqs.spectrum.items.trinkets.InkDrainTrinketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

@Mixin({InkAssortmentItem.class, PigmentPaletteItem.class, ArtistsPaletteItem.class, InkDrainTrinketItem.class})
public abstract class InkStorageItemMixin<T extends InkStorage> extends Item implements InkStorageItem<T> {

	private InkStorageItemMixin(Item.Settings properties) {
		super(properties);
	}

	@Override
	public void appendStacks(ItemGroup tab, DefaultedList<ItemStack> items) {
		super.appendStacks(tab, items);
		if(isIn(tab)) {
			items.add(getFullStack());
		}
	}
}
