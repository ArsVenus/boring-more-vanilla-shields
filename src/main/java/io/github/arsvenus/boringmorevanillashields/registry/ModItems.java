package io.github.arsvenus.boringmorevanillashields.registry;

import io.github.arsvenus.boringmorevanillashields.BoringMoreVanillaShields;
import io.github.arsvenus.boringmorevanillashields.item.ShieldMaterial;
import io.github.arsvenus.boringmorevanillashields.item.VariantShieldItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BoringMoreVanillaShields.MOD_ID);

    public static final DeferredItem<VariantShieldItem> LEATHER_SHIELD = register(ShieldMaterial.LEATHER);
    public static final DeferredItem<VariantShieldItem> COPPER_SHIELD = register(ShieldMaterial.COPPER);
    public static final DeferredItem<VariantShieldItem> GOLDEN_SHIELD = register(ShieldMaterial.GOLDEN);
    public static final DeferredItem<VariantShieldItem> DIAMOND_SHIELD = register(ShieldMaterial.DIAMOND);
    public static final DeferredItem<VariantShieldItem> NETHERITE_SHIELD = register(ShieldMaterial.NETHERITE);

    private ModItems() {
    }

    private static DeferredItem<VariantShieldItem> register(ShieldMaterial material) {
        return ITEMS.register(material.serializedName() + "_shield", () -> new VariantShieldItem(material));
    }
}
