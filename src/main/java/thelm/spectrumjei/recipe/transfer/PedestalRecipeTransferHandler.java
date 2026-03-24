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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeHolder;
import thelm.spectrumjei.SpectrumJEI;

public class PedestalRecipeTransferHandler implements IRecipeTransferHandler<PedestalScreenHandler, RecipeHolder<PedestalRecipe>> {

	public final PedestalRecipeTier tier;
	public final RecipeType<RecipeHolder<PedestalRecipe>> recipeType;
	public final IRecipeTransferHandler<PedestalScreenHandler, RecipeHolder<PedestalRecipe>> wrappedGridHandler;

	public PedestalRecipeTransferHandler(PedestalRecipeTier tier, IRecipeTransferHandlerHelper transferHelper) {
		this.tier = tier;
		recipeType = getRecipeType(tier);
		wrappedGridHandler = transferHelper.createUnregisteredRecipeTransferHandler(new GridRecipeTransferInfo(recipeType));
	}

	public static RecipeType<RecipeHolder<PedestalRecipe>> getRecipeType(PedestalRecipeTier tier) {
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
	public Optional<MenuType<PedestalScreenHandler>> getMenuType() {
		return Optional.of(SpectrumScreenHandlerTypes.PEDESTAL);
	}

	@Override
	public RecipeType<RecipeHolder<PedestalRecipe>> getRecipeType() {
		return recipeType;
	}

	@Override
	public IRecipeTransferError transferRecipe(PedestalScreenHandler container, RecipeHolder<PedestalRecipe> recipeHolder, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {
		return wrappedGridHandler.transferRecipe(container, recipeHolder, () -> filterGridSlots(recipeSlots), player, maxTransfer, doTransfer);
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

		public GridRecipeTransferInfo(RecipeType<RecipeHolder<PedestalRecipe>> recipeType) {
			super(PedestalScreenHandler.class, SpectrumScreenHandlerTypes.PEDESTAL, recipeType, 0, 9, 16, 36);
		}

		@Override
		public boolean canHandle(PedestalScreenHandler container, RecipeHolder<PedestalRecipe> recipeHolder) {
			return super.canHandle(container, recipeHolder) && container.getPedestalRecipeTier().compareTo(tier) >= 0;
		}
	}
}
