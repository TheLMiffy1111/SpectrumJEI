package thelm.spectrumjei.gui.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.dafuqs.spectrum.inventories.CraftingTabletScreen;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipeTier;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.RecipeType;
import thelm.spectrumjei.SpectrumJEI;

public class CraftingTabletRecipeClickAreaHandler implements IGuiContainerHandler<CraftingTabletScreen> {

	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(CraftingTabletScreen containerScreen, double guiMouseX, double guiMouseY) {
		PedestalRecipeTier tier = containerScreen.getMenu().getTier().orElse(PedestalRecipeTier.BASIC);
		List<RecipeType<?>> recipeTypes = new ArrayList<>();
		recipeTypes.add(RecipeTypes.CRAFTING);
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
		return List.of(IGuiClickableArea.createBasic(89, 37, 22, 16, recipeTypes.toArray(RecipeType<?>[]::new)));
	}
}
