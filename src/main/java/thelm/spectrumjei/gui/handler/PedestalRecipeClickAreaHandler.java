package thelm.spectrumjei.gui.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.inventories.PedestalScreen;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipeTier;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.RecipeType;
import thelm.spectrumjei.SpectrumJEI;

public class PedestalRecipeClickAreaHandler implements IGuiContainerHandler<PedestalScreen> {

	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(PedestalScreen containerScreen, double guiMouseX, double guiMouseY) {
		PedestalRecipeTier tier = containerScreen.getScreenHandler().getPedestalRecipeTier();
		List<RecipeType<?>> recipeTypes = new ArrayList<>();
		recipeTypes.add(SpectrumJEI.PEDESTAL_BASIC);
		if(tier.ordinal() > 0) {
			recipeTypes.add(SpectrumJEI.PEDESTAL_SIMPLE);
		}
		if(tier.ordinal() > 1) {
			recipeTypes.add(SpectrumJEI.PEDESTAL_ADVANCED);
		}
		if(tier.ordinal() > 2) {
			recipeTypes.add(SpectrumJEI.PEDESTAL_COMPLEX);
		}
		if(SpectrumCommon.CONFIG.canPedestalCraftVanillaRecipes()) {
			recipeTypes.add(RecipeTypes.CRAFTING);
		}
		return List.of(IGuiClickableArea.createBasic(89, 37, 22, 16, recipeTypes.toArray(RecipeType<?>[]::new)));
	}
}
