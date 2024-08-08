package com.c446.smp.entity.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MoonlightRayRender extends EntityRenderer<MoonlightRayEntity> {

    protected MoonlightRayRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(MoonlightRayEntity moonlightRayEntity) {
        return null;
    }

    @Override
    public void render(MoonlightRayEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light) {
        poseStack.pushPose();
        PoseStack.Pose pose = poseStack.last();
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        float oldWith = (float)entity.oldBB.getXsize();
        float width = entity.getBbWidth();
        width = oldWith + (width - oldWith) * Math.min(partialTick, 1.0F);


        this.drawRay(pose, entity, buffer, light, width, 4);

        this.drawRay(pose, entity, buffer, light, width, 0);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, light);
    }

    private void drawRay(PoseStack.Pose pose, MoonlightRayEntity entity, MultiBufferSource bufferSource, int light, float width, int offset) {
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity, offset)));
        float halfWidth = width * 0.5F;
        consumer.vertex(poseMatrix, -halfWidth, -0.1F, -halfWidth).color(90, 0, 10, 255).uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(poseMatrix, halfWidth, -0.1F, -halfWidth).color(90, 0, 10, 255).uv(1.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(poseMatrix, halfWidth, -0.1F, halfWidth).color(90, 0, 10, 255).uv(1.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(poseMatrix, -halfWidth, -0.1F, halfWidth).color(90, 0, 10, 255).uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).endVertex();
    }





}
