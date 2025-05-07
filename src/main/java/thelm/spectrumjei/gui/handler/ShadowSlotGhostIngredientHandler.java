package thelm.spectrumjei.gui.handler;

import java.util.List;

import de.dafuqs.spectrum.api.block.FilterConfigurable;
import de.dafuqs.spectrum.inventories.slots.ShadowSlot;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import thelm.jeidrawables.mixin.AbstractContainerScreenAccessor;

public class ShadowSlotGhostIngredientHandler<T extends HandledScreen<?>> implements IGhostIngredientHandler<T>{

	@Override
	public <I> List<Target<I>> getTargetsTyped(T gui, ITypedIngredient<I> ingredient, boolean doStart) {
		if(ingredient.getType() == VanillaTypes.ITEM_STACK) {
			int windowId = gui.getScreenHandler().syncId;
			return gui.getScreenHandler().slots.stream().
					filter(s -> s instanceof ShadowSlot).
					filter(s -> s.inventory instanceof FilterConfigurable.FilterInventory).
					<Target<I>>map(s -> new ShadowSlotTarget<>(windowId, s, getSlotArea(gui, s))).
					toList();
		}
		return List.of();
	}

	@Override
	public void onComplete() {}

	public static Rect2i getSlotArea(HandledScreen<?> gui, Slot slot) {
		AbstractContainerScreenAccessor accessor = (AbstractContainerScreenAccessor)gui;
		return new Rect2i(accessor.jeidas$leftPos() + slot.x, accessor.jeidas$topPos() + slot.y, 16, 16);
	}

	public record ShadowSlotTarget<I>(int windowId, ShadowSlot slot, FilterConfigurable.FilterInventory inventory, Rect2i area) implements Target<I> {

		public ShadowSlotTarget(int windowId, Slot slot, Rect2i area) {
			this(windowId, (ShadowSlot)slot, (FilterConfigurable.FilterInventory)slot.inventory, area);
		}

		@Override
		public Rect2i getArea() {
			return area;
		}

		@Override
		public void accept(I ingredient) {
			if(ingredient instanceof ItemStack stack) {
				inventory.getClicker().clickShadowSlot(windowId, slot, stack);
			}
		}
	}
}
