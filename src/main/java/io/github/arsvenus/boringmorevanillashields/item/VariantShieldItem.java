package io.github.arsvenus.boringmorevanillashields.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

public final class VariantShieldItem extends ShieldItem {
    private final ShieldMaterial material;

    public VariantShieldItem(ShieldMaterial material) {
        super(properties(material));
        this.material = material;
    }

    private static Item.Properties properties(ShieldMaterial material) {
        Item.Properties properties = new Item.Properties().durability(material.durability());
        return material == ShieldMaterial.NETHERITE ? properties.fireResistant() : properties;
    }

    public ShieldMaterial material() {
        return material;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(material.repairItem()) || super.isValidRepairItem(toRepair, repair);
    }
}
