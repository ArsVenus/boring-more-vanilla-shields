package io.github.arsvenus.boringmorevanillashields.recipe;

import io.github.arsvenus.boringmorevanillashields.item.VariantShieldItem;
import io.github.arsvenus.boringmorevanillashields.registry.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public final class VariantShieldDecorationRecipe extends CustomRecipe {
    public VariantShieldDecorationRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        ItemStack shield = ItemStack.EMPTY;
        ItemStack banner = ItemStack.EMPTY;

        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack candidate = input.getItem(slot);
            if (candidate.isEmpty()) {
                continue;
            }

            if (candidate.getItem() instanceof BannerItem) {
                if (!banner.isEmpty()) {
                    return false;
                }
                banner = candidate;
            } else if (candidate.getItem() instanceof VariantShieldItem) {
                if (!shield.isEmpty()) {
                    return false;
                }
                BannerPatternLayers patterns = candidate.getOrDefault(
                        DataComponents.BANNER_PATTERNS,
                        BannerPatternLayers.EMPTY
                );
                if (!patterns.layers().isEmpty()) {
                    return false;
                }
                shield = candidate;
            } else {
                return false;
            }
        }

        return !shield.isEmpty() && !banner.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack shield = ItemStack.EMPTY;
        ItemStack banner = ItemStack.EMPTY;

        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack candidate = input.getItem(slot);
            if (candidate.getItem() instanceof BannerItem) {
                banner = candidate;
            } else if (candidate.getItem() instanceof VariantShieldItem) {
                shield = candidate.copyWithCount(1);
            }
        }

        if (shield.isEmpty() || banner.isEmpty()) {
            return ItemStack.EMPTY;
        }

        shield.set(DataComponents.BANNER_PATTERNS, banner.getOrDefault(
                DataComponents.BANNER_PATTERNS,
                BannerPatternLayers.EMPTY
        ));
        shield.set(DataComponents.BASE_COLOR, ((BannerItem) banner.getItem()).getColor());
        return shield;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SHIELD_DECORATION.get();
    }
}
