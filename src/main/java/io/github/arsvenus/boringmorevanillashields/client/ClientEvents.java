package io.github.arsvenus.boringmorevanillashields.client;

import io.github.arsvenus.boringmorevanillashields.BoringMoreVanillaShields;
import io.github.arsvenus.boringmorevanillashields.registry.ModItems;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = BoringMoreVanillaShields.MOD_ID, value = Dist.CLIENT)
public final class ClientEvents {
    private static final ResourceLocation BLOCKING = ResourceLocation.withDefaultNamespace("blocking");

    private ClientEvents() {
    }

    @SubscribeEvent
    static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            private VariantShieldRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new VariantShieldRenderer();
                }
                return renderer;
            }
        }, ModItems.LEATHER_SHIELD, ModItems.COPPER_SHIELD, ModItems.GOLDEN_SHIELD,
                ModItems.DIAMOND_SHIELD, ModItems.NETHERITE_SHIELD);
    }

    @SubscribeEvent
    static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ModItems.ITEMS.getEntries().forEach(item ->
                ItemProperties.register(item.get(), BLOCKING, (stack, level, entity, seed) ->
                        entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
                )
        ));
    }
}
