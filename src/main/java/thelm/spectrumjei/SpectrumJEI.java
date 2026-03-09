package thelm.spectrumjei;

import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.blocks.idols.FirestarterIdolBlock;
import de.dafuqs.spectrum.blocks.idols.FreezingIdolBlock;
import de.dafuqs.spectrum.config.SpectrumConfig;
import de.dafuqs.spectrum.data_loaders.NaturesStaffConversionDataLoader;
import de.dafuqs.spectrum.inventories.BlackHoleChestScreen;
import de.dafuqs.spectrum.inventories.CinderhearthScreen;
import de.dafuqs.spectrum.inventories.CinderhearthScreenHandler;
import de.dafuqs.spectrum.inventories.CraftingTabletScreen;
import de.dafuqs.spectrum.inventories.CraftingTabletScreenHandler;
import de.dafuqs.spectrum.inventories.FilteringScreen;
import de.dafuqs.spectrum.inventories.PedestalScreen;
import de.dafuqs.spectrum.inventories.PedestalScreenHandler;
import de.dafuqs.spectrum.inventories.PotionWorkshopScreen;
import de.dafuqs.spectrum.inventories.PotionWorkshopScreenHandler;
import de.dafuqs.spectrum.inventories.QuickNavigationGridScreen;
import de.dafuqs.spectrum.inventories.SpectrumScreenHandlerTypes;
import de.dafuqs.spectrum.recipe.anvil_crushing.AnvilCrushingRecipe;
import de.dafuqs.spectrum.recipe.cinderhearth.CinderhearthRecipe;
import de.dafuqs.spectrum.recipe.color_picker.InkConvertingRecipe;
import de.dafuqs.spectrum.recipe.crystallarieum.CrystallarieumRecipe;
import de.dafuqs.spectrum.recipe.enchanter.EnchanterRecipe;
import de.dafuqs.spectrum.recipe.enchanter.EnchantmentUpgradeRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.DragonrotConvertingRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.LiquidCrystalConvertingRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.MidnightSolutionConvertingRecipe;
import de.dafuqs.spectrum.recipe.fluid_converting.SludgeConvertingRecipe;
import de.dafuqs.spectrum.recipe.fusion_shrine.FusionShrineRecipe;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipe;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipeTier;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopBrewingRecipe;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopCraftingRecipe;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopReactingRecipe;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import de.dafuqs.spectrum.recipe.spirit_instiller.SpiritInstillerRecipe;
import de.dafuqs.spectrum.recipe.titration_barrel.ITitrationBarrelRecipe;
import de.dafuqs.spectrum.registries.SpectrumAdvancements;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.ModList;
import thelm.spectrumjei.gui.handler.CraftingTabletRecipeClickAreaHandler;
import thelm.spectrumjei.gui.handler.OverlayHidingExtraAreaHandler;
import thelm.spectrumjei.gui.handler.PedestalRecipeClickAreaHandler;
import thelm.spectrumjei.gui.handler.ShadowSlotGhostIngredientHandler;
import thelm.spectrumjei.ingredient.subtype.ExperienceStorageItemSubtypeInterpreter;
import thelm.spectrumjei.ingredient.subtype.InkStorageItemSubtypeInterpreter;
import thelm.spectrumjei.ingredient.subtype.UnstableItemSubtypeInterpreter;
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
import thelm.spectrumjei.recipe.category.PrimordialFireBurningRecipeCategory;
import thelm.spectrumjei.recipe.category.SpiritInstillerRecipeCategory;
import thelm.spectrumjei.recipe.category.TitrationBarrelRecipeCategory;
import thelm.spectrumjei.recipe.transfer.CraftingTabletRecipeTransferHandler;
import thelm.spectrumjei.recipe.transfer.GatedRecipeTransferInfo;
import thelm.spectrumjei.recipe.transfer.PedestalRecipeTransferInfo;

@JeiPlugin
public class SpectrumJEI implements IModPlugin {

	public static final ResourceLocation UID = ResourceLocation.parse("spectrumjei:spectrum");
	public static final Logger LOGGER = LogManager.getLogger();

	public static IJeiHelpers jeiHelpers;
	public static IJeiRuntime jeiRuntime;

	//public static final List<RecipeType<?>> RECIPE_TYPES = new ArrayList<>();
	public static final RecipeType<RecipeHolder<PedestalRecipe>> PEDESTAL_BASIC = RecipeType.createRecipeHolderType(SpectrumCommon.locate("pedestal_basic"));
	public static final RecipeType<RecipeHolder<PedestalRecipe>> PEDESTAL_SIMPLE = RecipeType.createRecipeHolderType(SpectrumCommon.locate("pedestal_simple"));
	public static final RecipeType<RecipeHolder<PedestalRecipe>> PEDESTAL_ADVANCED = RecipeType.createRecipeHolderType(SpectrumCommon.locate("pedestal_advanced"));
	public static final RecipeType<RecipeHolder<PedestalRecipe>> PEDESTAL_COMPLEX = RecipeType.createRecipeHolderType(SpectrumCommon.locate("pedestal_complex"));
	public static final RecipeType<RecipeHolder<AnvilCrushingRecipe>> ANVIL_CRUSHING = RecipeType.createRecipeHolderType(SpectrumCommon.locate("anvil_crushing"));
	public static final RecipeType<RecipeHolder<FusionShrineRecipe>> FUSION_SHRINE = RecipeType.createRecipeHolderType(SpectrumCommon.locate("fusion_shrine"));
	public static final RecipeType<RecipeHolder<EnchanterRecipe>> ENCHANTER = RecipeType.createRecipeHolderType(SpectrumCommon.locate("enchanter"));
	public static final RecipeType<RecipeHolder<EnchantmentUpgradeRecipe>> ENCHANTMENT_UPGRADE = RecipeType.createRecipeHolderType(SpectrumCommon.locate("enchantment_upgrade"));
	public static final RecipeType<RecipeHolder<PotionWorkshopBrewingRecipe>> POTION_WORKSHOP_BREWING = RecipeType.createRecipeHolderType(SpectrumCommon.locate("potion_workshop_brewing"));
	public static final RecipeType<RecipeHolder<PotionWorkshopCraftingRecipe>> POTION_WORKSHOP_CRAFTING = RecipeType.createRecipeHolderType(SpectrumCommon.locate("potion_workshop_crafting"));
	public static final RecipeType<RecipeHolder<PotionWorkshopReactingRecipe>> POTION_WORKSHOP_REACTING = RecipeType.createRecipeHolderType(SpectrumCommon.locate("potion_workshop_reacting"));
	public static final RecipeType<RecipeHolder<SludgeConvertingRecipe>> SLUDGE_CONVERTING = RecipeType.createRecipeHolderType(SpectrumCommon.locate("mud_converting"));
	public static final RecipeType<RecipeHolder<LiquidCrystalConvertingRecipe>> LIQUID_CRYSTAL_CONVERTING = RecipeType.createRecipeHolderType(SpectrumCommon.locate("liquid_crystal_converting"));
	public static final RecipeType<RecipeHolder<MidnightSolutionConvertingRecipe>> MIDNIGHT_SOLUTION_CONVERTING = RecipeType.createRecipeHolderType(SpectrumCommon.locate("midnight_solution_converting"));
	public static final RecipeType<RecipeHolder<DragonrotConvertingRecipe>> DRAGONROT_CONVERTING = RecipeType.createRecipeHolderType(SpectrumCommon.locate("dragonrot_converting"));
	public static final RecipeType<RecipeHolder<SpiritInstillerRecipe>> SPIRIT_INSTILLER = RecipeType.createRecipeHolderType(SpectrumCommon.locate("spirit_instiller"));
	public static final RecipeType<RecipeHolder<InkConvertingRecipe>> INK_CONVERTING = RecipeType.createRecipeHolderType(SpectrumCommon.locate("ink_converting"));
	public static final RecipeType<RecipeHolder<CrystallarieumRecipe>> CRYSTALLARIEUM = RecipeType.createRecipeHolderType(SpectrumCommon.locate("crystallarieum"));
	public static final RecipeType<RecipeHolder<CinderhearthRecipe>> CINDERHEARTH = RecipeType.createRecipeHolderType(SpectrumCommon.locate("cinderhearth"));
	public static final RecipeType<RecipeHolder<ITitrationBarrelRecipe>> TITRATION_BARREL = RecipeType.createRecipeHolderType(SpectrumCommon.locate("titration_barrel"));
	public static final RecipeType<RecipeHolder<PrimordialFireBurningRecipe>> PRIMORDIAL_FIRE_BURNING = RecipeType.createRecipeHolderType(SpectrumCommon.locate("primordial_fire_burning"));

	public static final RecipeType<BlockConversionRecipe> NATURES_STAFF = new RecipeType<>(SpectrumCommon.locate("natures_staff"), BlockConversionRecipe.class);
	public static final RecipeType<BlockConversionWithChanceRecipe> HEATING = new RecipeType<>(SpectrumCommon.locate("heating"), BlockConversionWithChanceRecipe.class);
	public static final RecipeType<BlockConversionWithChanceRecipe> FREEZING = new RecipeType<>(SpectrumCommon.locate("freezing"), BlockConversionWithChanceRecipe.class);

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		registration.registerSubtypeInterpreter(SpectrumItems.KNOWLEDGE_GEM.get(), new ExperienceStorageItemSubtypeInterpreter());
		registration.registerSubtypeInterpreter(SpectrumItems.MIDNIGHT_ABERRATION.get(), new UnstableItemSubtypeInterpreter());

		InkStorageItemSubtypeInterpreter ink = new InkStorageItemSubtypeInterpreter();
		registration.registerSubtypeInterpreter(SpectrumItems.INK_FLASK.get(), ink);
		registration.registerSubtypeInterpreter(SpectrumItems.INK_ASSORTMENT.get(), ink);
		registration.registerSubtypeInterpreter(SpectrumItems.PIGMENT_PALETTE.get(), ink);
		registration.registerSubtypeInterpreter(SpectrumItems.ARTISTS_PALETTE.get(), ink);
		registration.registerSubtypeInterpreter(SpectrumItems.SHIELDGRASP_AMULET.get(), ink);
		registration.registerSubtypeInterpreter(SpectrumItems.HEARTSINGERS_REWARD.get(), ink);
		registration.registerSubtypeInterpreter(SpectrumItems.GLOVES_OF_DAWNS_GRASP.get(), ink);
		registration.registerSubtypeInterpreter(SpectrumItems.RING_OF_PURSUIT.get(), ink);
		registration.registerSubtypeInterpreter(SpectrumItems.RING_OF_DENSER_STEPS.get(), ink);
		registration.registerSubtypeInterpreter(SpectrumItems.RING_OF_AERIAL_GRACE.get(), ink);
		registration.registerSubtypeInterpreter(SpectrumItems.LAURELS_OF_SERENITY.get(), ink);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		jeiHelpers = registration.getJeiHelpers();

		if(checkDisabled()) {
			return;
		}

		registration.addRecipeCategories(new PedestalRecipeCategory(PedestalRecipeTier.BASIC));
		registration.addRecipeCategories(new PedestalRecipeCategory(PedestalRecipeTier.SIMPLE));
		registration.addRecipeCategories(new PedestalRecipeCategory(PedestalRecipeTier.ADVANCED));
		registration.addRecipeCategories(new PedestalRecipeCategory(PedestalRecipeTier.COMPLEX));
		registration.addRecipeCategories(new AnvilCrushingRecipeCategory());
		registration.addRecipeCategories(new FusionShrineRecipeCategory());
		registration.addRecipeCategories(new EnchanterRecipeCategory());
		registration.addRecipeCategories(new EnchantmentUpgradeRecipeCategory());
		registration.addRecipeCategories(new PotionWorkshopRecipeCategory<>(POTION_WORKSHOP_BREWING, Component.translatable("container.spectrum.rei.potion_workshop_brewing.title")));
		registration.addRecipeCategories(new PotionWorkshopRecipeCategory<>(POTION_WORKSHOP_CRAFTING, Component.translatable("container.spectrum.rei.potion_workshop_crafting.title")));
		registration.addRecipeCategories(new DescriptiveGatedRecipeCategory<>(POTION_WORKSHOP_REACTING, Component.translatable("container.spectrum.rei.potion_workshop_reacting.title")));
		registration.addRecipeCategories(new FluidConvertingRecipeCategory<>(SLUDGE_CONVERTING, Component.translatable("container.spectrum.rei.sludge_converting.title")));
		registration.addRecipeCategories(new FluidConvertingRecipeCategory<>(LIQUID_CRYSTAL_CONVERTING, Component.translatable("container.spectrum.rei.liquid_crystal_converting.title")));
		registration.addRecipeCategories(new FluidConvertingRecipeCategory<>(MIDNIGHT_SOLUTION_CONVERTING, Component.translatable("container.spectrum.rei.midnight_solution_converting.title")));
		registration.addRecipeCategories(new FluidConvertingRecipeCategory<>(DRAGONROT_CONVERTING, Component.translatable("container.spectrum.rei.dragonrot_converting.title")));
		registration.addRecipeCategories(new SpiritInstillerRecipeCategory());
		registration.addRecipeCategories(new InkConvertingRecipeCategory());
		registration.addRecipeCategories(new CrystallarieumRecipeCategory());
		registration.addRecipeCategories(new CinderhearthRecipeCategory());
		registration.addRecipeCategories(new TitrationBarrelRecipeCategory());
		registration.addRecipeCategories(new PrimordialFireBurningRecipeCategory());

		registration.addRecipeCategories(new BlockConversionRecipeCategory(NATURES_STAFF, Component.translatable("item.spectrum.natures_staff"), SpectrumAdvancements.UNLOCK_NATURES_STAFF));
		registration.addRecipeCategories(new BlockConversionWithChanceRecipeCategory(HEATING, Component.translatable("container.spectrum.rei.heating.title"), SpectrumAdvancements.UNLOCK_IDOLS));
		registration.addRecipeCategories(new BlockConversionWithChanceRecipeCategory(FREEZING, Component.translatable("container.spectrum.rei.freezing.title"), SpectrumAdvancements.UNLOCK_IDOLS));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
		registration.addRecipes(PEDESTAL_BASIC, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.PEDESTAL).stream().
				filter(r -> r.value().getTier() == PedestalRecipeTier.BASIC).toList());
		registration.addRecipes(PEDESTAL_SIMPLE, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.PEDESTAL).stream().
				filter(r -> r.value().getTier() == PedestalRecipeTier.SIMPLE).toList());
		registration.addRecipes(PEDESTAL_ADVANCED, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.PEDESTAL).stream().
				filter(r -> r.value().getTier() == PedestalRecipeTier.ADVANCED).toList());
		registration.addRecipes(PEDESTAL_COMPLEX, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.PEDESTAL).stream().
				filter(r -> r.value().getTier() == PedestalRecipeTier.COMPLEX).toList());
		registration.addRecipes(ANVIL_CRUSHING, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.ANVIL_CRUSHING));
		registration.addRecipes(FUSION_SHRINE, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.FUSION_SHRINE));
		registration.addRecipes(ENCHANTER, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.ENCHANTER));
		registration.addRecipes(ENCHANTMENT_UPGRADE, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.ENCHANTMENT_UPGRADE));
		registration.addRecipes(POTION_WORKSHOP_BREWING, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.POTION_WORKSHOP_BREWING));
		registration.addRecipes(POTION_WORKSHOP_CRAFTING, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.POTION_WORKSHOP_CRAFTING));
		registration.addRecipes(POTION_WORKSHOP_REACTING, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.POTION_WORKSHOP_REACTING));
		registration.addRecipes(SLUDGE_CONVERTING, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.SLUDGE_CONVERTING));
		registration.addRecipes(LIQUID_CRYSTAL_CONVERTING, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.LIQUID_CRYSTAL_CONVERTING));
		registration.addRecipes(MIDNIGHT_SOLUTION_CONVERTING, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.MIDNIGHT_SOLUTION_CONVERTING));
		registration.addRecipes(DRAGONROT_CONVERTING, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.DRAGONROT_CONVERTING));
		registration.addRecipes(SPIRIT_INSTILLER, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.SPIRIT_INSTILLING));
		registration.addRecipes(INK_CONVERTING, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.INK_CONVERTING));
		registration.addRecipes(CRYSTALLARIEUM, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.CRYSTALLARIEUM));
		registration.addRecipes(CINDERHEARTH, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.CINDERHEARTH));
		registration.addRecipes(TITRATION_BARREL, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.TITRATION_BARREL));
		registration.addRecipes(PRIMORDIAL_FIRE_BURNING, recipeManager.getAllRecipesFor(SpectrumRecipeTypes.PRIMORDIAL_FIRE_BURNING));

		registration.addRecipes(NATURES_STAFF,
				NaturesStaffConversionDataLoader.CONVERSIONS.entrySet().stream().
				map(entry -> new BlockConversionRecipe(entry.getKey(), entry.getValue())).
				filter(BlockConversionRecipe::isViewable).toList());
		registration.addRecipes(HEATING,
				FirestarterIdolBlock.BURNING_MAP.entrySet().stream().
				map(entry -> new BlockConversionWithChanceRecipe(entry.getKey(), entry.getValue().getA(), entry.getValue().getB())).
				filter(BlockConversionWithChanceRecipe::isViewable).toList());
		registration.addRecipes(FREEZING,
				Stream.concat(
						FreezingIdolBlock.FREEZING_STATE_MAP.entrySet().stream().
						map(entry -> new BlockConversionWithChanceRecipe(entry.getKey(), entry.getValue().getA(), entry.getValue().getB())),
						FreezingIdolBlock.FREEZING_MAP.entrySet().stream().
						map(entry -> new BlockConversionWithChanceRecipe(entry.getKey(), entry.getValue().getA(), entry.getValue().getB()))).
				filter(BlockConversionWithChanceRecipe::isViewable).toList());
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		if(checkDisabled()) {
			return;
		}

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
		if(checkDisabled()) {
			return;
		}

		registration.addRecipeCatalyst(SpectrumBlocks.FABRICATION_CHEST, RecipeTypes.CRAFTING);

		registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_BASIC_TOPAZ, PEDESTAL_BASIC);
		registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST, PEDESTAL_BASIC);
		registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_BASIC_CITRINE, PEDESTAL_BASIC);
		registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_ALL_BASIC, PEDESTAL_BASIC, PEDESTAL_SIMPLE);
		registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_ONYX, PEDESTAL_BASIC, PEDESTAL_SIMPLE, PEDESTAL_ADVANCED);
		registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_MOONSTONE, PEDESTAL_BASIC, PEDESTAL_SIMPLE, PEDESTAL_ADVANCED, PEDESTAL_COMPLEX);
		if(SpectrumConfig.CONFIG.canPedestalCraftVanillaRecipes()) {
			registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_BASIC_TOPAZ, RecipeTypes.CRAFTING);
			registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST, RecipeTypes.CRAFTING);
			registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_BASIC_CITRINE, RecipeTypes.CRAFTING);
			registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_ALL_BASIC, RecipeTypes.CRAFTING);
			registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_ONYX, RecipeTypes.CRAFTING);
			registration.addRecipeCatalyst(SpectrumBlocks.PEDESTAL_MOONSTONE, RecipeTypes.CRAFTING);
		}
		registration.addRecipeCatalyst(SpectrumItems.CRAFTING_TABLET, RecipeTypes.CRAFTING);
		registration.addRecipeCatalyst(Blocks.ANVIL, ANVIL_CRUSHING);
		registration.addRecipeCatalyst(SpectrumBlocks.BEDROCK_ANVIL, ANVIL_CRUSHING);
		registration.addRecipeCatalyst(SpectrumBlocks.STRATINE_FLOATBLOCK, ANVIL_CRUSHING);
		registration.addRecipeCatalyst(SpectrumBlocks.PALTAERIA_FLOATBLOCK, ANVIL_CRUSHING);
		registration.addRecipeCatalyst(SpectrumBlocks.FUSION_SHRINE_BASALT, FUSION_SHRINE);
		registration.addRecipeCatalyst(SpectrumBlocks.FUSION_SHRINE_CALCITE, FUSION_SHRINE);
		registration.addRecipeCatalyst(SpectrumBlocks.ENCHANTER, ENCHANTER, ENCHANTMENT_UPGRADE);
		registration.addRecipeCatalyst(SpectrumBlocks.POTION_WORKSHOP, POTION_WORKSHOP_BREWING, POTION_WORKSHOP_CRAFTING, POTION_WORKSHOP_REACTING);
		registration.addRecipeCatalyst(SpectrumItems.SLUDGE_BUCKET, SLUDGE_CONVERTING);
		registration.addRecipeCatalyst(SpectrumItems.LIQUID_CRYSTAL_BUCKET, LIQUID_CRYSTAL_CONVERTING);
		registration.addRecipeCatalyst(SpectrumItems.MIDNIGHT_SOLUTION_BUCKET, MIDNIGHT_SOLUTION_CONVERTING);
		registration.addRecipeCatalyst(SpectrumItems.DRAGONROT_BUCKET, DRAGONROT_CONVERTING);
		registration.addRecipeCatalyst(SpectrumBlocks.SPIRIT_INSTILLER, SPIRIT_INSTILLER);
		registration.addRecipeCatalyst(SpectrumBlocks.COLOR_PICKER, INK_CONVERTING);
		registration.addRecipeCatalyst(SpectrumBlocks.CRYSTALLARIEUM, CRYSTALLARIEUM);
		registration.addRecipeCatalyst(SpectrumBlocks.CINDERHEARTH, CINDERHEARTH, RecipeTypes.BLASTING);
		registration.addRecipeCatalyst(SpectrumBlocks.TITRATION_BARREL, TITRATION_BARREL);
		registration.addRecipeCatalyst(SpectrumItems.DOOMBLOOM_SEED, PRIMORDIAL_FIRE_BURNING);
		registration.addRecipeCatalyst(SpectrumItems.PRIMORDIAL_LIGHTER, PRIMORDIAL_FIRE_BURNING);
		registration.addRecipeCatalyst(SpectrumBlocks.INCANDESCENT_AMALGAM, PRIMORDIAL_FIRE_BURNING);
		registration.addRecipeCatalyst(SpectrumItems.PIPE_BOMB, PRIMORDIAL_FIRE_BURNING);

		registration.addRecipeCatalyst(SpectrumItems.NATURES_STAFF, NATURES_STAFF);
		registration.addRecipeCatalyst(SpectrumBlocks.BLAZE_IDOL, HEATING);
		registration.addRecipeCatalyst(SpectrumBlocks.POLAR_BEAR_IDOL, FREEZING);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		registration.addGuiContainerHandler(PedestalScreen.class, new PedestalRecipeClickAreaHandler());
		registration.addGuiContainerHandler(CraftingTabletScreen.class, new CraftingTabletRecipeClickAreaHandler());
		registration.addRecipeClickArea(PotionWorkshopScreen.class, 28, 41, 12, 42, POTION_WORKSHOP_BREWING, POTION_WORKSHOP_CRAFTING, POTION_WORKSHOP_REACTING);
		registration.addRecipeClickArea(CinderhearthScreen.class, 35, 31, 22, 16, CINDERHEARTH, RecipeTypes.BLASTING);

		registration.addGuiContainerHandler(QuickNavigationGridScreen.class, new OverlayHidingExtraAreaHandler<>());

		registration.addGhostIngredientHandler(BlackHoleChestScreen.class, new ShadowSlotGhostIngredientHandler<>());
		registration.addGhostIngredientHandler(FilteringScreen.class, new ShadowSlotGhostIngredientHandler<>());
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		SpectrumJEI.jeiRuntime = jeiRuntime;
	}

	public boolean checkDisabled() {
		if(ModList.get().isLoaded("rei_plugin_compatibilities")) {
			LOGGER.warn("SpectrumJEI is disabled with REIPC as Spectrum has native REI support");
			return true;
		}
		if(ModList.get().isLoaded("emi")) {
			LOGGER.warn("SpectrumJEI is disabled with EMI as Spectrum has native EMI support");
			return true;
		}
		return false;
	}
}
