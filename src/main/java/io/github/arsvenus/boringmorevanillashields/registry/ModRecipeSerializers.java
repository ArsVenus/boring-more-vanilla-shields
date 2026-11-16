package io.github.arsvenus.boringmorevanillashields.registry;

import io.github.arsvenus.boringmorevanillashields.BoringMoreVanillaShields;
import io.github.arsvenus.boringmorevanillashields.recipe.VariantShieldDecorationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, BoringMoreVanillaShields.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<VariantShieldDecorationRecipe>>
            SHIELD_DECORATION = RECIPE_SERIALIZERS.register(
                    "shield_decoration",
                    () -> new SimpleCraftingRecipeSerializer<>(VariantShieldDecorationRecipe::new)
            );

    private ModRecipeSerializers() {
    }
}
