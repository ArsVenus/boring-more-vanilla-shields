package io.github.arsvenus.boringmorevanillashields.item;

import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public enum ShieldMaterial {
    LEATHER("leather", 196, () -> Items.LEATHER, true),
    COPPER("copper", 420, () -> Items.COPPER_INGOT, false),
    GOLDEN("golden", 322, () -> Items.GOLD_INGOT, false),
    DIAMOND("diamond", 1024, () -> Items.DIAMOND, false),
    NETHERITE("netherite", 2042, () -> Items.NETHERITE_INGOT, false);

    private final String serializedName;
    private final int durability;
    private final Supplier<Item> repairItem;
    private final boolean dyeable;

    ShieldMaterial(String serializedName, int durability, Supplier<Item> repairItem, boolean dyeable) {
        this.serializedName = serializedName;
        this.durability = durability;
        this.repairItem = repairItem;
        this.dyeable = dyeable;
    }

    public String serializedName() {
        return serializedName;
    }

    public int durability() {
        return durability;
    }

    public Item repairItem() {
        return repairItem.get();
    }

    public boolean dyeable() {
        return dyeable;
    }
}
