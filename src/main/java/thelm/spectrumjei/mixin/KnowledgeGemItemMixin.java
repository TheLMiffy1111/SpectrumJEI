package thelm.spectrumjei.mixin;

import org.spongepowered.asm.mixin.Mixin;

import de.dafuqs.spectrum.items.magic_items.KnowledgeGemItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

@Mixin(KnowledgeGemItem.class)
public abstract class KnowledgeGemItemMixin extends Item {

	private KnowledgeGemItemMixin(Item.Settings properties) {
		super(properties);
	}

	@Override
	public void appendStacks(ItemGroup tab, DefaultedList<ItemStack> items) {
		super.appendStacks(tab, items);
		if(isIn(tab)) {
			items.add(KnowledgeGemItem.getKnowledgeDropStackWithXP(10000, false));
		}
	}
}
