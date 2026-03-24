package thelm.spectrumjei.recipe.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dafuqs.spectrum.inventories.PedestalScreenHandler;
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

public class PedestalRecipeTransferHandler implements IRecipeTransferHandler<PedestalScreenHandler, PedestalRecipe> {

	public final PedestalRecipeTier tier;
	public final RecipeType<PedestalRecipe> recipeType;
	public final IRecipeTransferHandler<PedestalScreenHandler, PedestalRecipe> wrappedGridHandler;

	public PedestalRecipeTransferHandler(PedestalRecipeTier tier, IRecipeTransferHandlerHelper transferHelper) {
		this.tier = tier;
		recipeType = getRecipeType(tier);
		wrappedGridHandler = transferHelper.createUnregisteredRecipeTransferHandler(new GridRecipeTransferInfo(recipeType));
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
	public Class<PedestalScreenHandler> getContainerClass() {
		return PedestalScreenHandler.class;
	}

	@Override
	public Optional<ScreenHandlerType<PedestalScreenHandler>> getMenuType() {
		return Optional.of(SpectrumScreenHandlerTypes.PEDESTAL);
	}

	@Override
	public RecipeType<PedestalRecipe> getRecipeType() {
		return recipeType;
	}

	@Override
	public IRecipeTransferError transferRecipe(PedestalScreenHandler container, PedestalRecipe recipe, IRecipeSlotsView recipeSlots, PlayerEntity player, boolean maxTransfer, boolean doTransfer) {
		return wrappedGridHandler.transferRecipe(container, recipe, () -> filterGridSlots(recipeSlots), player, maxTransfer, doTransfer);
	}

	public List<IRecipeSlotView> filterGridSlots(IRecipeSlotsView recipeSlots) {
		List<IRecipeSlotView> original = recipeSlots.getSlotViews();
		List<IRecipeSlotView> filtered = new ArrayList<>();
		int inputCount = 0;
		for(IRecipeSlotView slot : original) {
			if(slot.getRole() == RecipeIngredientRole.INPUT) {
				if(inputCount++ >= 9) {
					continue;
				}
			}
			filtered.add(slot);
		}
		return filtered;
	}

	public class GridRecipeTransferInfo extends GatedRecipeTransferInfo<PedestalScreenHandler, PedestalRecipe> {

		public GridRecipeTransferInfo(RecipeType<PedestalRecipe> recipeType) {
			super(PedestalScreenHandler.class, SpectrumScreenHandlerTypes.PEDESTAL, recipeType, 0, 9, 16, 36);
		}

		@Override
		public boolean canHandle(PedestalScreenHandler container, PedestalRecipe recipe) {
			return super.canHandle(container, recipe) && container.getPedestalRecipeTier().compareTo(tier) >= 0;
		}
	}
}
