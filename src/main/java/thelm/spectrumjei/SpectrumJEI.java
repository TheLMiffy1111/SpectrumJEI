package thelm.spectrumjei;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.blocks.mob_blocks.FirestarterMobBlock;
import de.dafuqs.spectrum.blocks.mob_blocks.FreezingMobBlock;
import de.dafuqs.spectrum.data_loaders.NaturesStaffConversionDataLoader;
import de.dafuqs.spectrum.inventories.CinderhearthScreen;
import de.dafuqs.spectrum.inventories.CinderhearthScreenHandler;
import de.dafuqs.spectrum.inventories.CraftingTabletScreen;
import de.dafuqs.spectrum.inventories.CraftingTabletScreenHandler;
import de.dafuqs.spectrum.inventories.PedestalScreen;
import de.dafuqs.spectrum.inventories.PedestalScreenHandler;
import de.dafuqs.spectrum.inventories.PotionWorkshopScreen;
import de.dafuqs.spectrum.inventories.PotionWorkshopScreenHandler;
import de.dafuqs.spectrum.inventories.QuickNavigationGridScreen;
import de.dafuqs.spectrum.inventories.SpectrumScreenHandlerTypes;
import de.dafuqs.spectrum.recipe.SpectrumRecipeTypes;
import de.dafuqs.spectrum.recipe.anvil_crushing.AnvilCrushingRecipe;
import de.dafuqs.spectrum.recipe.cinderhearth.CinderhearthRecipe;
import de.dafuqs.spectrum.recipe.crystallarieum.CrystallarieumRecipe;
import de.dafuqs.spectrum.recipe.enchanter.EnchanterRecipe;
import de.dafuqs.spectrum.recipe.enchantment_upgrade.EnchantmentUpgradeRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.DragonrotConvertingRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.LiquidCrystalConvertingRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.MidnightSolutionConvertingRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.MudConvertingRecipe;
import de.dafuqs.spectrum.recipe.fusion_shrine.FusionShrineRecipe;
import de.dafuqs.spectrum.recipe.ink_converting.InkConvertingRecipe;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipe;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipeTier;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopBrewingRecipe;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopCraftingRecipe;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopReactingRecipe;
import de.dafuqs.spectrum.recipe.spirit_instiller.SpiritInstillerRecipe;
import de.dafuqs.spectrum.recipe.titration_barrel.ITitrationBarrelRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thelm.spectrumjei.gui.handler.CraftingTabletRecipeClickAreaHandler;
import thelm.spectrumjei.gui.handler.OverlayHidingExtraAreaHandler;
import thelm.spectrumjei.gui.handler.PedestalRecipeClickAreaHandler;
import thelm.spectrumjei.recipe.BlockConversionRecipe;
import thelm.spectrumjei.recipe.BlockConversionWithChanceRecipe;
import thelm.spectrumjei.recipe.category.AnvilCrushingRecipeCategory;
import thelm.spectrumjei.recipe.category.BlockConversionRecipeCategory;
import thelm.spectrumjei.recipe.category.BlockConversionWithChanceRecipeCategory;
import thelm.spectrumjei.recipe.category.CinderhearthRecipeCategory;
import thelm.spectrumjei.recipe.category.CrystallarieumRecipeCategory;
import thelm.spectrumjei.recipe.category.DescriptiveGatedRecipeCategory;
import thelm.spectrumjei.recipe.category.EnchanterRecipeCategory;
import thelm.spectrumjei.recipe.category.EnchantmentUpgradeRecipeCategory;
import thelm.spectrumjei.recipe.category.FluidConvertingRecipeCategory;
import thelm.spectrumjei.recipe.category.FusionShrineRecipeCategory;
import thelm.spectrumjei.recipe.category.InkConvertingRecipeCategory;
import thelm.spectrumjei.recipe.category.PedestalRecipeCategory;
import thelm.spectrumjei.recipe.category.PotionWorkshopRecipeCategory;
import thelm.spectrumjei.recipe.category.SpiritInstillerRecipeCategory;
import thelm.spectrumjei.recipe.category.TitrationBarrelRecipeCategory;
import thelm.spectrumjei.recipe.transfer.CraftingTabletRecipeTransferHandler;
import thelm.spectrumjei.recipe.transfer.GatedRecipeTransferInfo;
import thelm.spectrumjei.recipe.transfer.PedestalRecipeTransferInfo;

public class SpectrumJEI implements IModPlugin {

	public static final Identifier UID = new Identifier("spectrumjei:spectrum");

	public static IJeiHelpers jeiHelpers;
	public static IJeiRuntime jeiRuntime;

	public static final List<RecipeType<?>> RECIPE_TYPES = new ArrayList<>();
	public static final RecipeType<PedestalRecipe> PEDESTAL_BASIC = createRecipeType(SpectrumCommon.locate("pedestal_basic"), PedestalRecipe.class);
	public static final RecipeType<PedestalRecipe> PEDESTAL_SIMPLE = createRecipeType(SpectrumCommon.locate("pedestal_simple"), PedestalRecipe.class);
	public static final RecipeType<PedestalRecipe> PEDESTAL_ADVANCED = createRecipeType(SpectrumCommon.locate("pedestal_advanced"), PedestalRecipe.class);
	public static final RecipeType<PedestalRecipe> PEDESTAL_COMPLEX = createRecipeType(SpectrumCommon.locate("pedestal_complex"), PedestalRecipe.class);
	public static final RecipeType<AnvilCrushingRecipe> ANVIL_CRUSHING = createRecipeType(SpectrumCommon.locate("anvil_crushing"), AnvilCrushingRecipe.class);
	public static final RecipeType<FusionShrineRecipe> FUSION_SHRINE = createRecipeType(SpectrumCommon.locate("fusion_shrine"), FusionShrineRecipe.class);
	public static final RecipeType<EnchanterRecipe> ENCHANTER = createRecipeType(SpectrumCommon.locate("enchanter"), EnchanterRecipe.class);
	public static final RecipeType<EnchantmentUpgradeRecipe> ENCHANTMENT_UPGRADE = createRecipeType(SpectrumCommon.locate("enchantment_upgrade"), EnchantmentUpgradeRecipe.class);
	public static final RecipeType<PotionWorkshopBrewingRecipe> POTION_WORKSHOP_BREWING = createRecipeType(SpectrumCommon.locate("potion_workshop_brewing"), PotionWorkshopBrewingRecipe.class);
	public static final RecipeType<PotionWorkshopCraftingRecipe> POTION_WORKSHOP_CRAFTING = createRecipeType(SpectrumCommon.locate("potion_workshop_crafting"), PotionWorkshopCraftingRecipe.class);
	public static final RecipeType<PotionWorkshopReactingRecipe> POTION_WORKSHOP_REACTING = createRecipeType(SpectrumCommon.locate("potion_workshop_reacting"), PotionWorkshopReactingRecipe.class);
	public static final RecipeType<MudConvertingRecipe> MUD_CONVERTING = createRecipeType(SpectrumCommon.locate("mud_converting"), MudConvertingRecipe.class);
	public static final RecipeType<LiquidCrystalConvertingRecipe> LIQUID_CRYSTAL_CONVERTING = createRecipeType(SpectrumCommon.locate("liquid_crystal_converting"), LiquidCrystalConvertingRecipe.class);
	public static final RecipeType<MidnightSolutionConvertingRecipe> MIDNIGHT_SOLUTION_CONVERTING = createRecipeType(SpectrumCommon.locate("midnight_solution_converting"), MidnightSolutionConvertingRecipe.class);
	public static final RecipeType<DragonrotConvertingRecipe> DRAGONROT_CONVERTING = createRecipeType(SpectrumCommon.locate("dragonrot_converting"), DragonrotConvertingRecipe.class);
	public static final RecipeType<SpiritInstillerRecipe> SPIRIT_INSTILLER = createRecipeType(SpectrumCommon.locate("spirit_instiller"), SpiritInstillerRecipe.class);
	public static final RecipeType<InkConvertingRecipe> INK_CONVERTING = createRecipeType(SpectrumCommon.locate("ink_converting"), InkConvertingRecipe.class);
	public static final RecipeType<CrystallarieumRecipe> CRYSTALLARIEUM = createRecipeType(SpectrumCommon.locate("crystallarieum"), CrystallarieumRecipe.class);
	public static final RecipeType<CinderhearthRecipe> CINDERHEARTH = createRecipeType(SpectrumCommon.locate("cinderhearth"), CinderhearthRecipe.class);
	public static final RecipeType<ITitrationBarrelRecipe> TITRATION_BARREL = createRecipeType(SpectrumCommon.locate("titration_barrel"), ITitrationBarrelRecipe.class);

	public static final RecipeType<BlockConversionRecipe> NATURES_STAFF = createRecipeType(SpectrumCommon.locate("natures_staff"), BlockConversionRecipe.class);
	public static final RecipeType<BlockConversionWithChanceRecipe> HEATING = createRecipeType(SpectrumCommon.locate("heating"), BlockConversionWithChanceRecipe.class);
	public static final RecipeType<BlockConversionWithChanceRecipe> FREEZING = createRecipeType(SpectrumCommon.locate("freezing"), BlockConversionWithChanceRecipe.class);

	@Override
	public Identifier getPluginUid() {
		return UID;
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		jeiHelpers = registration.getJeiHelpers();

		registration.addRecipeCategories(new PedestalRecipeCategory(PedestalRecipeTier.BASIC));
		registration.addRecipeCategories(new PedestalRecipeCategory(PedestalRecipeTier.SIMPLE));
		registration.addRecipeCategories(new PedestalRecipeCategory(PedestalRecipeTier.ADVANCED));
		registration.addRecipeCategories(new PedestalRecipeCategory(PedestalRecipeTier.COMPLEX));
		registration.addRecipeCategories(new AnvilCrushingRecipeCategory());
		registration.addRecipeCategories(new FusionShrineRecipeCategory());
		registration.addRecipeCategories(new EnchanterRecipeCategory());
		registration.addRecipeCategories(new EnchantmentUpgradeRecipeCategory());
		registration.addRecipeCategories(new PotionWorkshopRecipeCategory<>(POTION_WORKSHOP_BREWING, Text.translatable("container.spectrum.rei.potion_workshop_brewing.title")));
		registration.addRecipeCategories(new PotionWorkshopRecipeCategory<>(POTION_WORKSHOP_CRAFTING, Text.translatable("container.spectrum.rei.potion_workshop_crafting.title")));
		registration.addRecipeCategories(new DescriptiveGatedRecipeCategory<>(POTION_WORKSHOP_REACTING, Text.translatable("container.spectrum.rei.potion_workshop_reacting.title")));
		registration.addRecipeCategories(new FluidConvertingRecipeCategory<>(MUD_CONVERTING, Text.translatable("container.spectrum.rei.mud_converting.title")));
		registration.addRecipeCategories(new FluidConvertingRecipeCategory<>(LIQUID_CRYSTAL_CONVERTING, Text.translatable("container.spectrum.rei.liquid_crystal_converting.title")));
		registration.addRecipeCategories(new FluidConvertingRecipeCategory<>(MIDNIGHT_SOLUTION_CONVERTING, Text.translatable("container.spectrum.rei.midnight_solution_converting.title")));
		registration.addRecipeCategories(new FluidConvertingRecipeCategory<>(DRAGONROT_CONVERTING, Text.translatable("container.spectrum.rei.dragonrot_converting.title")));
		registration.addRecipeCategories(new SpiritInstillerRecipeCategory());
		registration.addRecipeCategories(new InkConvertingRecipeCategory());
		registration.addRecipeCategories(new CrystallarieumRecipeCategory());
		registration.addRecipeCategories(new CinderhearthRecipeCategory());
		registration.addRecipeCategories(new TitrationBarrelRecipeCategory());

		registration.addRecipeCategories(new BlockConversionRecipeCategory(NATURES_STAFF, Text.translatable("item.spectrum.natures_staff"), SpectrumCommon.locate("unlocks/items/natures_staff")));
		registration.addRecipeCategories(new BlockConversionWithChanceRecipeCategory(HEATING, Text.translatable("container.spectrum.rei.heating.title"), SpectrumCommon.locate("unlocks/blocks/mob_blocks")));
		registration.addRecipeCategories(new BlockConversionWithChanceRecipeCategory(FREEZING, Text.translatable("container.spectrum.rei.freezing.title"), SpectrumCommon.locate("unlocks/blocks/mob_blocks")));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = MinecraftClient.getInstance().world.getRecipeManager();
		registration.addRecipes(PEDESTAL_BASIC, recipeManager.listAllOfType(SpectrumRecipeTypes.PEDESTAL).stream().
				filter(r -> r.getTier() == PedestalRecipeTier.BASIC).toList());
		registration.addRecipes(PEDESTAL_SIMPLE, recipeManager.listAllOfType(SpectrumRecipeTypes.PEDESTAL).stream().
				filter(r -> r.getTier() == PedestalRecipeTier.SIMPLE).toList());
		registration.addRecipes(PEDESTAL_ADVANCED, recipeManager.listAllOfType(SpectrumRecipeTypes.PEDESTAL).stream().
				filter(r -> r.getTier() == PedestalRecipeTier.ADVANCED).toList());
		registration.addRecipes(PEDESTAL_COMPLEX, recipeManager.listAllOfType(SpectrumRecipeTypes.PEDESTAL).stream().
				filter(r -> r.getTier() == PedestalRecipeTier.COMPLEX).toList());
		registration.addRecipes(ANVIL_CRUSHING, recipeManager.listAllOfType(SpectrumRecipeTypes.ANVIL_CRUSHING));
		registration.addRecipes(FUSION_SHRINE, recipeManager.listAllOfType(SpectrumRecipeTypes.FUSION_SHRINE));
		registration.addRecipes(ENCHANTER, recipeManager.listAllOfType(SpectrumRecipeTypes.ENCHANTER));
		registration.addRecipes(ENCHANTMENT_UPGRADE, recipeManager.listAllOfType(SpectrumRecipeTypes.ENCHANTMENT_UPGRADE));
		registration.addRecipes(POTION_WORKSHOP_BREWING, recipeManager.listAllOfType(SpectrumRecipeTypes.POTION_WORKSHOP_BREWING));
		registration.addRecipes(POTION_WORKSHOP_CRAFTING, recipeManager.listAllOfType(SpectrumRecipeTypes.POTION_WORKSHOP_CRAFTING));
		registration.addRecipes(POTION_WORKSHOP_REACTING, recipeManager.listAllOfType(SpectrumRecipeTypes.POTION_WORKSHOP_REACTING));
		registration.addRecipes(MUD_CONVERTING, recipeManager.listAllOfType(SpectrumRecipeTypes.MUD_CONVERTING));
		registration.addRecipes(LIQUID_CRYSTAL_CONVERTING, recipeManager.listAllOfType(SpectrumRecipeTypes.LIQUID_CRYSTAL_CONVERTING));
		registration.addRecipes(MIDNIGHT_SOLUTION_CONVERTING, recipeManager.listAllOfType(SpectrumRecipeTypes.MIDNIGHT_SOLUTION_CONVERTING));
		registration.addRecipes(DRAGONROT_CONVERTING, recipeManager.listAllOfType(SpectrumRecipeTypes.DRAGONROT_CONVERTING));
		registration.addRecipes(SPIRIT_INSTILLER, recipeManager.listAllOfType(SpectrumRecipeTypes.SPIRIT_INSTILLING));
		registration.addRecipes(INK_CONVERTING, recipeManager.listAllOfType(SpectrumRecipeTypes.INK_CONVERTING));
		registration.addRecipes(CRYSTALLARIEUM, recipeManager.listAllOfType(SpectrumRecipeTypes.CRYSTALLARIEUM));
		registration.addRecipes(CINDERHEARTH, recipeManager.listAllOfType(SpectrumRecipeTypes.CINDERHEARTH));
		registration.addRecipes(TITRATION_BARREL, recipeManager.listAllOfType(SpectrumRecipeTypes.TITRATION_BARREL));

		registration.addRecipes(NATURES_STAFF,
				NaturesStaffConversionDataLoader.CONVERSIONS.entrySet().stream().
				map(entry -> new BlockConversionRecipe(entry.getKey(), entry.getValue())).
				filter(BlockConversionRecipe::isViewable).toList());
		registration.addRecipes(HEATING,
				FirestarterMobBlock.BURNING_MAP.entrySet().stream().
				map(entry -> new BlockConversionWithChanceRecipe(entry.getKey(), entry.getValue().getLeft(), entry.getValue().getRight())).
				filter(BlockConversionWithChanceRecipe::isViewable).toList());
		registration.addRecipes(FREEZING,
				Stream.concat(
						FreezingMobBlock.FREEZING_STATE_MAP.entrySet().stream().
						map(entry -> new BlockConversionWithChanceRecipe(entry.getKey(), entry.getValue().getLeft(), entry.getValue().getRight())),
						FreezingMobBlock.FREEZING_MAP.entrySet().stream().
						map(entry -> new BlockConversionWithChanceRecipe(entry.getKey(), entry.getValue().getLeft(), entry.getValue().getRight()))).
				filter(BlockConversionWithChanceRecipe::isViewable).toList());
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		IRecipeTransferHandlerHelper transferHelper = registration.getTransferHelper();
		registration.addRecipeTransferHandler(new PedestalRecipeTransferInfo(PedestalRecipeTier.BASIC));
		registration.addRecipeTransferHandler(new PedestalRecipeTransferInfo(PedestalRecipeTier.SIMPLE));
		registration.addRecipeTransferHandler(new PedestalRecipeTransferInfo(PedestalRecipeTier.ADVANCED));
		registration.addRecipeTransferHandler(new PedestalRecipeTransferInfo(PedestalRecipeTier.COMPLEX));
		registration.addRecipeTransferHandler(PedestalScreenHandler.class, SpectrumScreenHandlerTypes.PEDESTAL, RecipeTypes.CRAFTING, 0, 9, 16, 36);
		registration.addRecipeTransferHandler(new CraftingTabletRecipeTransferHandler(PedestalRecipeTier.BASIC, transferHelper), PEDESTAL_BASIC);
		registration.addRecipeTransferHandler(new CraftingTabletRecipeTransferHandler(PedestalRecipeTier.SIMPLE, transferHelper), PEDESTAL_SIMPLE);
		registration.addRecipeTransferHandler(new CraftingTabletRecipeTransferHandler(PedestalRecipeTier.ADVANCED, transferHelper), PEDESTAL_ADVANCED);
		registration.addRecipeTransferHandler(new CraftingTabletRecipeTransferHandler(PedestalRecipeTier.COMPLEX, transferHelper), PEDESTAL_COMPLEX);
		registration.addRecipeTransferHandler(CraftingTabletScreenHandler.class, SpectrumScreenHandlerTypes.CRAFTING_TABLET, RecipeTypes.CRAFTING, 0, 9, 15, 36);
		registration.addRecipeTransferHandler(new GatedRecipeTransferInfo<>(PotionWorkshopScreenHandler.class, SpectrumScreenHandlerTypes.POTION_WORKSHOP, POTION_WORKSHOP_BREWING, 0, 9, 21, 36));
		registration.addRecipeTransferHandler(new GatedRecipeTransferInfo<>(PotionWorkshopScreenHandler.class, SpectrumScreenHandlerTypes.POTION_WORKSHOP, POTION_WORKSHOP_CRAFTING, 0, 9, 21, 36));
		registration.addRecipeTransferHandler(new GatedRecipeTransferInfo<>(CinderhearthScreenHandler.class, SpectrumScreenHandlerTypes.CINDERHEARTH, CINDERHEARTH, 2, 1, 11, 36));
		registration.addRecipeTransferHandler(CinderhearthScreenHandler.class, SpectrumScreenHandlerTypes.CINDERHEARTH, RecipeTypes.BLASTING, 2, 1, 11, 36);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.RESTOCKING_CHEST), RecipeTypes.CRAFTING);

		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_TOPAZ), PEDESTAL_BASIC);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST), PEDESTAL_BASIC);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_CITRINE), PEDESTAL_BASIC);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_ALL_BASIC), PEDESTAL_BASIC, PEDESTAL_SIMPLE);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_ONYX), PEDESTAL_BASIC, PEDESTAL_SIMPLE, PEDESTAL_ADVANCED);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_MOONSTONE), PEDESTAL_BASIC, PEDESTAL_SIMPLE, PEDESTAL_ADVANCED, PEDESTAL_COMPLEX);
		if(SpectrumCommon.CONFIG.canPedestalCraftVanillaRecipes()) {
			registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_TOPAZ), RecipeTypes.CRAFTING);
			registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST), RecipeTypes.CRAFTING);
			registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_BASIC_CITRINE), RecipeTypes.CRAFTING);
			registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_ALL_BASIC), RecipeTypes.CRAFTING);
			registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_ONYX), RecipeTypes.CRAFTING);
			registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PEDESTAL_MOONSTONE), RecipeTypes.CRAFTING);
		}
		registration.addRecipeCatalyst(new ItemStack(SpectrumItems.CRAFTING_TABLET), RecipeTypes.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(Blocks.ANVIL), ANVIL_CRUSHING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.BEDROCK_ANVIL), ANVIL_CRUSHING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.STRATINE_FRAGMENT_BLOCK), ANVIL_CRUSHING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.PALTAERIA_FRAGMENT_BLOCK), ANVIL_CRUSHING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.FUSION_SHRINE_BASALT), FUSION_SHRINE);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.FUSION_SHRINE_CALCITE), FUSION_SHRINE);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.ENCHANTER), ENCHANTER, ENCHANTMENT_UPGRADE);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.POTION_WORKSHOP), POTION_WORKSHOP_BREWING, POTION_WORKSHOP_CRAFTING, POTION_WORKSHOP_REACTING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumItems.MUD_BUCKET), MUD_CONVERTING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumItems.LIQUID_CRYSTAL_BUCKET), LIQUID_CRYSTAL_CONVERTING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumItems.MIDNIGHT_SOLUTION_BUCKET), MIDNIGHT_SOLUTION_CONVERTING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumItems.DRAGONROT_BUCKET), DRAGONROT_CONVERTING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.SPIRIT_INSTILLER), SPIRIT_INSTILLER);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.COLOR_PICKER), INK_CONVERTING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.CRYSTALLARIEUM), CRYSTALLARIEUM);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.CINDERHEARTH), CINDERHEARTH, RecipeTypes.BLASTING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.TITRATION_BARREL), TITRATION_BARREL);

		registration.addRecipeCatalyst(new ItemStack(SpectrumItems.NATURES_STAFF), NATURES_STAFF);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.BLAZE_MOB_BLOCK), HEATING);
		registration.addRecipeCatalyst(new ItemStack(SpectrumBlocks.POLAR_BEAR_MOB_BLOCK), FREEZING);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addGuiContainerHandler(PedestalScreen.class, new PedestalRecipeClickAreaHandler());
		registration.addGuiContainerHandler(CraftingTabletScreen.class, new CraftingTabletRecipeClickAreaHandler());
		registration.addRecipeClickArea(PotionWorkshopScreen.class, 28, 41, 12, 42, POTION_WORKSHOP_BREWING, POTION_WORKSHOP_CRAFTING, POTION_WORKSHOP_REACTING);
		registration.addRecipeClickArea(CinderhearthScreen.class, 35, 31, 22, 16, CINDERHEARTH, RecipeTypes.BLASTING);

		registration.addGuiContainerHandler(QuickNavigationGridScreen.class, new OverlayHidingExtraAreaHandler<>());
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		SpectrumJEI.jeiRuntime = jeiRuntime;
	}

	public static <R> RecipeType<R> createRecipeType(Identifier uid, Class<? extends R> recipeClass) {
		RecipeType<R> recipeType = new RecipeType<>(uid, recipeClass);
		RECIPE_TYPES.add(recipeType);
		return recipeType;
	}
}
