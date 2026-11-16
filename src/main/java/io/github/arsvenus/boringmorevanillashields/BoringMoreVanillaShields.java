package io.github.arsvenus.boringmorevanillashields;

import io.github.arsvenus.boringmorevanillashields.registry.ModItems;
import io.github.arsvenus.boringmorevanillashields.registry.ModRecipeSerializers;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(BoringMoreVanillaShields.MOD_ID)
public final class BoringMoreVanillaShields {
    public static final String MOD_ID = "boring_more_vanilla_shields";

    public BoringMoreVanillaShields(IEventBus modEventBus) {
        ModItems.ITEMS.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        modEventBus.addListener(this::addCreativeTabItems);
    }

    private void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.LEATHER_SHIELD);
            event.accept(ModItems.COPPER_SHIELD);
            event.accept(ModItems.GOLDEN_SHIELD);
            event.accept(ModItems.DIAMOND_SHIELD);
            event.accept(ModItems.NETHERITE_SHIELD);
        }
    }
}
