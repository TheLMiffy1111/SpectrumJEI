package thelm.spectrumjei.recipe.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dafuqs.spectrum.inventories.CraftingTabletScreenHandler;
import de.dafuqs.spectrum.inventories.SpectrumScreenHandlerTypes;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipe;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipeTier;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandlerType;
import thelm.spectrumjei.SpectrumJEI;

public class CraftingTabletRecipeTransferHandler implements IRecipeTransferHandler<CraftingTabletScreenHandler, PedestalRecipe> {

	public final PedestalRecipeTier tier;
	public final RecipeType<PedestalRecipe> recipeType;
	public final IRecipeTransferHandler<CraftingTabletScreenHandler, PedestalRecipe> wrappedHandler;

	public CraftingTabletRecipeTransferHandler(PedestalRecipeTier tier, IRecipeTransferHandlerHelper transferHelper) {
		this.tier = tier;
		recipeType = getRecipeType(tier);
		wrappedHandler = transferHelper.createUnregisteredRecipeTransferHandler(new RecipeTransferInfo(recipeType));
	}

	public static RecipeType<PedestalRecipe> getRecipeType(PedestalRecipeTier tier) {
		return switch(tier) {
		case BASIC -> SpectrumJEI.PEDESTAL_BASIC;
		case SIMPLE -> SpectrumJEI.PEDESTAL_SIMPLE;
		case ADVANCED -> SpectrumJEI.PEDESTAL_ADVANCED;
		case COMPLEX -> SpectrumJEI.PEDESTAL_COMPLEX;
		};
	}

	@Override
	public Class<CraftingTabletScreenHandler> getContainerClass() {
		return CraftingTabletScreenHandler.class;
	}

	@Override
	public Optional<ScreenHandlerType<CraftingTabletScreenHandler>> getMenuType() {
		return Optional.of(SpectrumScreenHandlerTypes.CRAFTING_TABLET);
	}

	@Override
	public RecipeType<PedestalRecipe> getRecipeType() {
		return recipeType;
	}

	@Override
	public IRecipeTransferError transferRecipe(CraftingTabletScreenHandler container, PedestalRecipe recipe, IRecipeSlotsView recipeSlots, PlayerEntity player, boolean maxTransfer, boolean doTransfer) {
		return wrappedHandler.transferRecipe(container, recipe, () -> filterSlots(recipeSlots), player, maxTransfer, doTransfer);
	}

	public List<IRecipeSlotView> filterSlots(IRecipeSlotsView recipeSlots) {
		List<IRecipeSlotView> original = recipeSlots.getSlotViews();
		List<IRecipeSlotView> filtered = new ArrayList<>();
		int inputCount = 0;
		for(IRecipeSlotView slot : original) {
			if(slot.getRole() == RecipeIngredientRole.INPUT) {
				if(inputCount >= 9) {
					continue;
				}
				inputCount++;
			}
			filtered.add(slot);
		}
		return filtered;
	}

	public class RecipeTransferInfo extends GatedRecipeTransferInfo<CraftingTabletScreenHandler, PedestalRecipe> {

		public RecipeTransferInfo(RecipeType<PedestalRecipe> recipeType) {
			super(CraftingTabletScreenHandler.class, SpectrumScreenHandlerTypes.CRAFTING_TABLET, recipeType, 0, 9, 15, 36);
		}

		@Override
		public boolean canHandle(CraftingTabletScreenHandler container, PedestalRecipe recipe) {
			return super.canHandle(container, recipe) && container.getTier().orElse(PedestalRecipeTier.BASIC).compareTo(tier) >= 0;
		}
	}
}
