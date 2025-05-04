package thelm.spectrumjei.recipe;

import java.util.List;

import mezz.jei.api.helpers.IPlatformFluidHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import thelm.spectrumjei.SpectrumJEI;

public record BlockConversionRecipe(BlockState input, BlockState output) {

	public BlockConversionRecipe(Block input, BlockState output) {
		this(input.getDefaultState(), output);
	}

	public List<?> inputIngredient() {
		return toIngredient(input);
	}

	public List<?> outputIngredient() {
		return toIngredient(output);
	}

	public boolean isViewable() {
		return !inputIngredient().isEmpty() && !outputIngredient().isEmpty();
	}

	public static List<?> toIngredient(BlockState state) {
		if(state.getBlock() instanceof FluidBlock) {
			IPlatformFluidHelper<?> fluidHelper = SpectrumJEI.jeiHelpers.getPlatformFluidHelper();
			Fluid fluid = state.getFluidState().getFluid();
			if(fluid instanceof FlowableFluid fFluid) {
				fluid = fFluid.getStill();
			}
			return List.of(fluidHelper.create(fluid, fluidHelper.bucketVolume()));
		}
		else {
			ItemStack stack = new ItemStack(state.getBlock());
			return stack.isEmpty() ? List.of() : List.of(stack);
		}
	}
}
