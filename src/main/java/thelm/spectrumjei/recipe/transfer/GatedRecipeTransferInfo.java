package thelm.spectrumjei.recipe.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.spectrum.api.recipe.GatedRecipe;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

public class GatedRecipeTransferInfo<C extends ScreenHandler, R extends GatedRecipe<?>> implements IRecipeTransferInfo<C, R> {

	public final Class<? extends C> containerClass;
	public final ScreenHandlerType<C> menuType;
	public final RecipeType<R> recipeType;
	public final int recipeSlotStart;
	public final int recipeSlotCount;
	public final int inventorySlotStart;
	public final int inventorySlotCount;

	public GatedRecipeTransferInfo(Class<? extends C> containerClass, ScreenHandlerType<C> menuType, RecipeType<R> recipeType, int recipeSlotStart, int recipeSlotCount, int inventorySlotStart, int inventorySlotCount) {
		this.containerClass = containerClass;
		this.menuType = menuType;
		this.recipeType = recipeType;
		this.recipeSlotStart = recipeSlotStart;
		this.recipeSlotCount = recipeSlotCount;
		this.inventorySlotStart = inventorySlotStart;
		this.inventorySlotCount = inventorySlotCount;
	}

	@Override
	public Class<? extends C> getContainerClass() {
		return containerClass;
	}

	@Override
	public Optional<ScreenHandlerType<C>> getMenuType() {
		return Optional.ofNullable(menuType);
	}

	@Override
	public RecipeType<R> getRecipeType() {
		return recipeType;
	}

	@Override
	public boolean canHandle(C container, R recipe) {
		return isVisible(recipe);
	}

	@Override
	public List<Slot> getRecipeSlots(C container, R recipe) {
		List<Slot> slots = new ArrayList<>(recipeSlotCount);
		for(int i = recipeSlotStart; i < recipeSlotStart + recipeSlotCount; i++) {
			Slot slot = container.getSlot(i);
			slots.add(slot);
		}
		return slots;
	}

	@Override
	public List<Slot> getInventorySlots(C container, R recipe) {
		List<Slot> slots = new ArrayList<>(inventorySlotCount);
		for(int i = inventorySlotStart; i < inventorySlotStart + inventorySlotCount; i++) {
			Slot slot = container.getSlot(i);
			slots.add(slot);
		}
		return slots;
	}

	public boolean isUnlocked(R recipe) {
		return hasAdvancement(recipe.getRecipeTypeUnlockIdentifier()) && hasAdvancement(recipe.getRequiredAdvancementIdentifier());
	}

	public boolean isVisible(R recipe) {
		return isUnlocked(recipe) && !recipe.isSecret();
	}

	public boolean hasAdvancement(Identifier advancement) {
		return advancement == null || AdvancementHelper.hasAdvancementClient(advancement);
	}
}
