package io.github.arsvenus.boringmorevanillashields.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.arsvenus.boringmorevanillashields.BoringMoreVanillaShields;
import io.github.arsvenus.boringmorevanillashields.item.ShieldMaterial;
import io.github.arsvenus.boringmorevanillashields.item.VariantShieldItem;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public final class VariantShieldRenderer extends BlockEntityWithoutLevelRenderer {
    private static final Material LEATHER_BASE = material("leather_base");
    private static final Material LEATHER_NO_PATTERN = material("leather_base_nopattern");
    private static final Material LEATHER_RIM = material("leather_rim");
    private static final Map<ShieldMaterial, Material> BASE_MATERIALS = createMaterials("");
    private static final Map<ShieldMaterial, Material> NO_PATTERN_MATERIALS = createMaterials("_nopattern");

    private final ShieldModel shieldModel;

    public VariantShieldRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        shieldModel = new ShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.SHIELD));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (!(stack.getItem() instanceof VariantShieldItem shieldItem)) {
            return;
        }

        BannerPatternLayers patterns = stack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
        DyeColor baseColor = stack.get(DataComponents.BASE_COLOR);
        boolean decorated = !patterns.layers().isEmpty() || baseColor != null;

        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);

        if (shieldItem.material().dyeable()) {
            renderLeather(stack, decorated, patterns, baseColor, poseStack, buffer, packedLight, packedOverlay);
        } else {
            renderMaterialShield(
                    stack,
                    shieldItem.material(),
                    decorated,
                    patterns,
                    baseColor,
                    poseStack,
                    buffer,
                    packedLight,
                    packedOverlay
            );
        }

        poseStack.popPose();
    }

    private void renderMaterialShield(ItemStack stack, ShieldMaterial materialType, boolean decorated,
            BannerPatternLayers patterns, DyeColor baseColor, PoseStack poseStack, MultiBufferSource buffer,
            int packedLight, int packedOverlay) {
        Material material = decorated ? BASE_MATERIALS.get(materialType) : NO_PATTERN_MATERIALS.get(materialType);
        VertexConsumer consumer = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(
                buffer,
                shieldModel.renderType(material.atlasLocation()),
                true,
                stack.hasFoil()
        ));

        shieldModel.handle().render(poseStack, consumer, packedLight, packedOverlay);
        if (decorated) {
            BannerRenderer.renderPatterns(
                    poseStack,
                    buffer,
                    packedLight,
                    packedOverlay,
                    shieldModel.plate(),
                    material,
                    false,
                    Objects.requireNonNullElse(baseColor, DyeColor.WHITE),
                    patterns,
                    stack.hasFoil()
            );
        } else {
            shieldModel.plate().render(poseStack, consumer, packedLight, packedOverlay);
        }
    }

    private void renderLeather(ItemStack stack, boolean decorated, BannerPatternLayers patterns, DyeColor baseColor,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Material background = decorated ? LEATHER_BASE : LEATHER_NO_PATTERN;
        VertexConsumer backgroundConsumer = background.buffer(buffer, RenderType::entityCutoutNoCull, stack.hasFoil());
        int leatherColor = DyedItemColor.getOrDefault(stack, DyedItemColor.LEATHER_COLOR);
        VertexConsumer rimConsumer = LEATHER_RIM.buffer(buffer, RenderType::entityCutoutNoCull, stack.hasFoil());

        shieldModel.handle().render(poseStack, backgroundConsumer, packedLight, packedOverlay);
        shieldModel.handle().render(poseStack, rimConsumer, packedLight, packedOverlay, leatherColor);

        if (decorated) {
            BannerRenderer.renderPatterns(
                    poseStack,
                    buffer,
                    packedLight,
                    packedOverlay,
                    shieldModel.plate(),
                    background,
                    false,
                    Objects.requireNonNullElse(baseColor, DyeColor.WHITE),
                    patterns,
                    stack.hasFoil()
            );
        } else {
            shieldModel.plate().render(poseStack, backgroundConsumer, packedLight, packedOverlay);
        }
        shieldModel.plate().render(poseStack, rimConsumer, packedLight, packedOverlay, leatherColor);
    }

    private static Map<ShieldMaterial, Material> createMaterials(String suffix) {
        Map<ShieldMaterial, Material> materials = new EnumMap<>(ShieldMaterial.class);
        for (ShieldMaterial material : ShieldMaterial.values()) {
            if (!material.dyeable()) {
                materials.put(material, material(material.serializedName() + "_base" + suffix));
            }
        }
        return Map.copyOf(materials);
    }

    private static Material material(String name) {
        return new Material(
                Sheets.SHIELD_SHEET,
                ResourceLocation.fromNamespaceAndPath(
                        BoringMoreVanillaShields.MOD_ID,
                        "entity/shield/" + name
                )
        );
    }
}
