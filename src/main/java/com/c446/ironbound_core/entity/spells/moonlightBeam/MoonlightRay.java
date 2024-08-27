package com.c446.ironbound_core.entity.spells.moonlightBeam;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public class MoonlightRay extends Projectile implements AntiMagicSusceptible {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(MoonlightRay.class, EntityDataSerializers.FLOAT);
    private static final double SPEED = 1.0;
    private static final int EXPIRE_TIME = 80;
    public final int animationSeed;
    private final float maxRadius;
    public AABB oldBB;
    public int animationTime;
    List<Entity> victims;
    private int age;
    private float damage;

    public MoonlightRay(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.animationSeed = Utils.random.nextInt(9999);
        this.setRadius(0.6F);
        this.maxRadius = 3.0F;
        this.oldBB = this.getBoundingBox();
        this.victims = new ArrayList<>();
        this.setNoGravity(true);
    }

    public MoonlightRay(EntityType<? extends MoonlightRay> entityType, Level levelIn, LivingEntity shooter) {
        this(entityType, levelIn);
        this.setOwner(shooter);
        this.setYRot(shooter.getYRot());
        this.setXRot(shooter.getXRot());
    }

    @Override
    public void onAntiMagic(MagicData magicData) {
        this.discard();
    }

    public float getRadius() {
        return this.getEntityData().get(DATA_RADIUS);
    }

    public void setRadius(float newRadius) {
        if (newRadius <= this.maxRadius && !this.level().isClientSide) {
            this.getEntityData().set(DATA_RADIUS, Mth.clamp(newRadius, 0.0F, this.maxRadius));
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public EntityDimensions getDimensions(Pose p_19721_) {
        this.getBoundingBox();
        return EntityDimensions.scalable(this.getRadius() * 2.0F, 0.5F);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> p_19729_) {
        if (DATA_RADIUS.equals(p_19729_)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(p_19729_);
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.discard();
    }

    private void damageEntity(Entity entity) {
        if (!this.victims.contains(entity)) {
            DamageSources.applyDamage(entity, this.damage, SpellRegistry.BLOOD_SLASH_SPELL.get().getDamageSource(this, this.getOwner()));
            this.victims.add(entity);
        }
    }

    public void spawnParticles() {
        if (this.level().isClientSide) {
            float width = (float) this.getBoundingBox().getXsize();
            float step = 0.25F;
            float radians = 0.017453292F * this.getYRot();
            float speed = 0.1F;

            for (int i = 0; (float) i < width / step; ++i) {
                double x = this.getX();
                double y = this.getY();
                double z = this.getZ();
                double offset = step * ((float) i - width / step / 2.0F);
                double rotX = offset * Math.cos(radians);
                double rotZ = -offset * Math.sin(radians);
                double dx = Math.random() * (double) speed * 2.0 - (double) speed;
                double dy = Math.random() * (double) speed * 2.0 - (double) speed;
                double dz = Math.random() * (double) speed * 2.0 - (double) speed;
                this.level().addParticle(ParticleTypes.SNOWFLAKE, false, x + rotX + dx, y + dy, z + rotZ + dz, dx, dy, dz);
            }
        }
    }
}
