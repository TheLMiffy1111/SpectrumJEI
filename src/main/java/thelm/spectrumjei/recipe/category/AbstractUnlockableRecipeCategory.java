package thelm.spectrumjei.recipe.category;

import java.util.List;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import thelm.jeidrawables.gui.render.BlankDrawable;
import thelm.spectrumjei.SpectrumJEI;

public abstract class AbstractUnlockableRecipeCategory<R> implements IRecipeCategory<R> {

	public static final Text HIDDEN_LINE_1 = new TranslatableText("container.spectrum.rei.pedestal_crafting.recipe_not_unlocked_line_1");
	public static final Text HIDDEN_LINE_2 = new TranslatableText("container.spectrum.rei.pedestal_crafting.recipe_not_unlocked_line_2");

	public final RecipeType<R> recipeType;
	public final Text title;
	public final IDrawable background;

	public AbstractUnlockableRecipeCategory(RecipeType<R> recipeType, Text title) {
		this.recipeType = recipeType;
		this.title = title;
		background = new BlankDrawable(getWidth(), getHeight());
	}

	@Override
	public Identifier getUid() {
		return recipeType.getUid();
	}

	@Override
	public Class<? extends R> getRecipeClass() {
		return recipeType.getRecipeClass();
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
	public IDrawable getBackground() {
		return background;
	}

	public int getWidth() {
		return 136;
	}

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
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		if(!isUnlocked(recipe)) {
			drawLockedText(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		}
	}

	public void drawLockedText(R recipe, IRecipeSlotsView recipeSlotsView, MatrixStack poseStack, double mouseX, double mouseY) {
		TextRenderer font = font();
		font.draw(poseStack, HIDDEN_LINE_1, getWidth() / 2 - font.getWidth(HIDDEN_LINE_1) / 2, getHeight() / 2 - 9, 0x3F3F3F);
		font.draw(poseStack, HIDDEN_LINE_2, getWidth() / 2 - font.getWidth(HIDDEN_LINE_2) / 2, getHeight() / 2 + 1, 0x3F3F3F);
	}

	public boolean hasAdvancement(Identifier advancement) {
		return advancement == null || AdvancementHelper.hasAdvancementClient(advancement);
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

	public IIngredientAcceptor<?> addFluid(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, Fluid fluid, long amount, IDrawable background, boolean visible) {
		IIngredientAcceptor<?> acceptor = addSlot(builder, ingredientRole, x, y, background, visible).addFluidStack(fluid, amount);
		if(acceptor instanceof IRecipeSlotBuilder slot) {
			slot.setFluidRenderer(amount, false, 16, 16);
		}
		return acceptor;
	}

	public Text getTimeComponent(int time) {
		return time == 20 ? new TranslatableText("container.spectrum.rei.enchanting.crafting_time_one_second", 1) : new TranslatableText("container.spectrum.rei.enchanting.crafting_time", time / 20);
	}

	public Text getTimeComponent(int time, float experience) {
		return time == 20 ? new TranslatableText("container.spectrum.rei.pedestal_crafting.crafting_time_one_second_and_xp", 1, experience) : new TranslatableText("container.spectrum.rei.pedestal_crafting.crafting_time_and_xp", time / 20, experience);
	}
}
