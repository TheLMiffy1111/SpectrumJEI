package thelm.spectrumjei.recipe.transfer;

import de.dafuqs.spectrum.inventories.PedestalScreenHandler;
import de.dafuqs.spectrum.inventories.SpectrumScreenHandlerTypes;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipe;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipeTier;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.crafting.RecipeHolder;
import thelm.spectrumjei.SpectrumJEI;

public class PedestalRecipeTransferInfo extends GatedRecipeTransferInfo<PedestalScreenHandler, PedestalRecipe> {

	public final PedestalRecipeTier tier;

	public PedestalRecipeTransferInfo(PedestalRecipeTier tier) {
		super(PedestalScreenHandler.class, SpectrumScreenHandlerTypes.PEDESTAL, getRecipeType(tier), 0, getRecipeSlotCount(tier), 16, 36);
		this.tier = tier;
	}

	public static RecipeType<RecipeHolder<PedestalRecipe>> getRecipeType(PedestalRecipeTier tier) {
		return switch(tier) {
		case BASIC -> SpectrumJEI.PEDESTAL_BASIC;
		case SIMPLE -> SpectrumJEI.PEDESTAL_SIMPLE;
		case ADVANCED -> SpectrumJEI.PEDESTAL_ADVANCED;
		case COMPLEX -> SpectrumJEI.PEDESTAL_COMPLEX;
		};
	}

	public static int getRecipeSlotCount(PedestalRecipeTier tier) {
		return switch(tier) {
		case BASIC, SIMPLE -> 12;
		case ADVANCED -> 13;
		case COMPLEX -> 14;
		};
	}

	@Override
	public boolean canHandle(PedestalScreenHandler container, RecipeHolder<PedestalRecipe> recipeHolder) {
		return super.canHandle(container, recipeHolder) && container.getPedestalRecipeTier().compareTo(tier) >= 0;
	}
}
