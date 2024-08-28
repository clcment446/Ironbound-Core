package com.c446.ironbound_core.entity.spells;

import com.c446.ironbound_core.Ironbound;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.redspace.ironsspellbooks.entity.spells.blood_slash.BloodSlashRenderer;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import software.bernie.geckolib.core.keyframe.KeyframeStack;

import java.util.function.Consumer;

public class MoonlightRayRenderer extends EntityRenderer<MoonlightRayEntity> {
    public MoonlightRayRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    private static final ResourceLocation TEXTURE = new ResourceLocation(Ironbound.MOD_ID, "texture/entity/moonlight_beam");
    private static ResourceLocation[] TEXTURES = new ResourceLocation[]{new ResourceLocation("textures/particle/sweep_0.png"), new ResourceLocation("textures/particle/sweep_1.png"), new ResourceLocation("textures/particle/sweep_2.png"), new ResourceLocation("textures/particle/sweep_3.png"), new ResourceLocation("textures/particle/sweep_4.png"), new ResourceLocation("textures/particle/sweep_5.png"), new ResourceLocation("textures/particle/sweep_6.png"), new ResourceLocation("textures/particle/sweep_7.png")};

    @Override
    public ResourceLocation getTextureLocation(MoonlightRayEntity moonlightRayEntity) {
        return null;
    }

    @Override
    public void render(MoonlightRayEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light) {
        poseStack.pushPose();
        PoseStack.Pose pose = poseStack.last();
        float oldWith = (float) entity.oldBB.getXsize();
        float width = entity.getBbWidth();
        width = oldWith + (width - oldWith) * Math.min(partialTick, 1.0F);
        this.drawRay(pose, entity, buffer, light, width, 4);
        this.drawRay(pose, entity, buffer, light, width, 0);
        poseStack.popPose();
    }

    private void drawRay(PoseStack.Pose pose, MoonlightRayEntity entity, MultiBufferSource bufferSource, int light, float width, int offset) {
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity)));
        float halfWidth = width * 0.5F;
        consumer.vertex(poseMatrix, -halfWidth, -0.1F, -halfWidth).color(90, 0, 10, 255).uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(poseMatrix, halfWidth, -0.1F, -halfWidth).color(90, 0, 10, 255).uv(1.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(poseMatrix, halfWidth, -0.1F, halfWidth).color(90, 0, 10, 255).uv(1.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(poseMatrix, -halfWidth, -0.1F, halfWidth).color(90, 0, 10, 255).uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).endVertex();
    }
}
