package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.spectrum.api.recipe.FluidIngredient;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import thelm.spectrumjei.SpectrumJEI;

public abstract class AbstractUnlockableRecipeCategory<R> implements IRecipeCategory<R> {

	public static final Text HIDDEN_LINE_1 = Text.translatable("container.spectrum.rei.pedestal_crafting.recipe_not_unlocked_line_1");
	public static final Text HIDDEN_LINE_2 = Text.translatable("container.spectrum.rei.pedestal_crafting.recipe_not_unlocked_line_2");
	public static final Text SECRET = Text.translatable("container.spectrum.rei.pedestal_crafting.secret_recipe");
	public static final Text SECRET_HINT = Text.translatable("container.spectrum.rei.pedestal_crafting.secret_recipe.hint");

	public final RecipeType<R> recipeType;
	public final Text title;

	public AbstractUnlockableRecipeCategory(RecipeType<R> recipeType, Text title) {
		this.recipeType = recipeType;
		this.title = title;
	}

	@Override
	public RecipeType<R> getRecipeType() {
		return recipeType;
	}

	@Override
	public Text getTitle() {
		return title;
	}

	@Override
	public int getWidth() {
		return 136;
	}

	@Override
	public abstract int getHeight();

	@Override
	public IDrawable getIcon() {
		return null;
	}

	public abstract boolean isUnlocked(R recipe);

	public boolean isVisible(R recipe) {
		return isUnlocked(recipe);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		if(!isUnlocked(recipe)) {
			drawLockedText(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		}
	}

	public void drawLockedText(R recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
		TextRenderer font = font();
		guiGraphics.drawText(font, HIDDEN_LINE_1, getWidth() / 2 - font.getWidth(HIDDEN_LINE_1) / 2, getHeight() / 2 - 9, 0x3F3F3F, false);
		guiGraphics.drawText(font, HIDDEN_LINE_2, getWidth() / 2 - font.getWidth(HIDDEN_LINE_2) / 2, getHeight() / 2 + 1, 0x3F3F3F, false);
	}

	public boolean hasAdvancement(Identifier advancement) {
		return advancement == null || AdvancementHelper.hasAdvancementClient(advancement);
	}

	public DynamicRegistryManager registryAccess() {
		return MinecraftClient.getInstance().world.getRegistryManager();
	}

	public TextRenderer font() {
		return MinecraftClient.getInstance().textRenderer;
	}

	public IJeiHelpers jeiHelpers() {
		return SpectrumJEI.jeiHelpers;
	}

	public IPlatformFluidHelper<?> fluidHelper() {
		return jeiHelpers().getPlatformFluidHelper();
	}

	public IGuiHelper guiHelper() {
		return jeiHelpers().getGuiHelper();
	}

	public IIngredientAcceptor<?> addSlot(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, IDrawable background, boolean visible) {
		return visible ? builder.addSlot(ingredientRole, x, y).setBackground(background, 8 - background.getWidth() / 2, 8 - background.getHeight() / 2) : builder.addInvisibleIngredients(ingredientRole);
	}

	public IIngredientAcceptor<?> addSlot(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, boolean visible) {
		return visible ? builder.addSlot(ingredientRole, x, y) : builder.addInvisibleIngredients(ingredientRole);
	}

	public IIngredientAcceptor<?> addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, List<ItemStack> itemStacks, IDrawable background, boolean visible) {
		return addSlot(builder, ingredientRole, x, y, background, visible).addItemStacks(itemStacks);
	}

	public IIngredientAcceptor<?> addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, List<ItemStack> itemStacks, boolean visible) {
		return addSlot(builder, ingredientRole, x, y, visible).addItemStacks(itemStacks);
	}

	public IIngredientAcceptor<?> addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, Ingredient ingredient, IDrawable background, boolean visible) {
		return addSlot(builder, ingredientRole, x, y, background, visible).addIngredients(ingredient);
	}

	public IIngredientAcceptor<?> addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, Ingredient ingredient, boolean visible) {
		return addSlot(builder, ingredientRole, x, y, visible).addIngredients(ingredient);
	}

	public IIngredientAcceptor<?> addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, ItemStack itemStack, IDrawable background, boolean visible) {
		return addSlot(builder, ingredientRole, x, y, background, visible).addItemStack(itemStack);
	}

	public IIngredientAcceptor<?> addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, ItemStack itemStack, boolean visible) {
		return addSlot(builder, ingredientRole, x, y, visible).addItemStack(itemStack);
	}

	public IIngredientAcceptor<?> addFluid(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, FluidIngredient ingredient, long amount, IDrawable background, boolean visible) {
		IIngredientAcceptor<?> acceptor = addSlot(builder, ingredientRole, x, y, background, visible);
		if(ingredient.isTag()) {
			List<Fluid> fluids = Registries.FLUID.getEntryList(ingredient.tag().get()).stream().
					flatMap(RegistryEntryList::stream).
					map(RegistryEntry::value).toList();
			for(Fluid fluid : fluids) {
				acceptor.addFluidStack(fluid, amount);
			}
		}
		else {
			acceptor.addFluidStack(ingredient.fluid().get(), amount);
		}
		if(acceptor instanceof IRecipeSlotBuilder slot) {
			slot.setFluidRenderer(amount, false, 16, 16);
		}
		return acceptor;
	}

	public Text getTimeComponent(int time) {
		return time == 20 ? Text.translatable("container.spectrum.rei.crafting_time_one_second", 1) : Text.translatable("container.spectrum.rei.crafting_time", time / 20);
	}

	public Text getTimeComponent(int time, float experience) {
		return time == 20 ? Text.translatable("container.spectrum.rei.crafting_time_one_second_and_xp", 1, experience) : Text.translatable("container.spectrum.rei.crafting_time_and_xp", time / 20, experience);
	}
}
