package com.c446.ironbound_core.entity.spells;

import com.c446.ironbound_core.entity.spells.moonlightBeam.MoonlightRay;
import com.c446.ironbound_core.registry.IronboundCoreEntities;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AbstractShieldEntity;
import io.redspace.ironsspellbooks.entity.spells.ShieldPart;
import io.redspace.ironsspellbooks.entity.spells.blood_slash.BloodSlashProjectile;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoonlightRayEntity extends Projectile implements AntiMagicSusceptible {
    public static final int EXPIRE_TIME = 100;
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(MoonlightRayEntity.class, EntityDataSerializers.FLOAT);
    private static final double SPEED = 1.0;
    public final int animationSeed;
    private final float maxRadius;
    public AABB oldBB;
    public int animationTime;
    private int age;
    private float damage;
    private final List<Entity> victims;

    public MoonlightRayEntity(EntityType<? extends MoonlightRayEntity> entityType, Level level) {
        super(entityType, level);
        this.animationSeed = Utils.random.nextInt(9999);
        this.setRadius(0.6F);
        this.maxRadius = 3.0F;
        this.oldBB = this.getBoundingBox();
        this.victims = new ArrayList<>();
        this.setNoGravity(true);
    }

    public MoonlightRayEntity(EntityType<? extends MoonlightRayEntity> entityType, Level levelIn, LivingEntity shooter) {
        this(entityType, levelIn);
        this.setOwner(shooter);
        this.setYRot(shooter.getYRot());
        this.setXRot(shooter.getXRot());
    }

    public MoonlightRayEntity(Level levelIn, LivingEntity shooter) {
        this((EntityType) IronboundCoreEntities.MOONLIGHT_RAY_ENTITY.get(), levelIn, shooter);
    }


    public void shoot(Vec3 rotation) {
        this.setDeltaMovement(rotation.scale(1.0));
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    protected void defineSynchedData() {
        this.getEntityData().define(DATA_RADIUS, 0.5F);
    }

    public float getRadius() {
        return this.getEntityData().get(DATA_RADIUS);
    }

    public void setRadius(float newRadius) {
        if (newRadius <= this.maxRadius && !this.level().isClientSide) {
            this.getEntityData().set(DATA_RADIUS, Mth.clamp(newRadius, 0.0F, this.maxRadius));
        }
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public void tick() {
        super.tick();
        if (++this.age > EXPIRE_TIME) {
            this.discard();
        } else {
            this.oldBB = this.getBoundingBox();
            this.setRadius(this.getRadius() + 0.12F);
            if (!this.level().isClientSide) {
                HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
                if (hitresult.getType() == HitResult.Type.BLOCK) {
                    this.onHitBlock((BlockHitResult) hitresult);
                }
                for (Entity currentTarget : this.level().getEntities(this, this.getBoundingBox()).stream().filter((target) -> {
                    return this.canHitEntity(target) && !this.victims.contains(target);
                }).collect(Collectors.toSet())) {
                    this.damageEntity(currentTarget);
                    MagicManager.spawnParticles(this.level(), ParticleHelper.BLOOD, currentTarget.getX(), currentTarget.getY(), currentTarget.getZ(), 50, 0.0, 0.0, 0.0, 0.5, true);
                    if (currentTarget instanceof ShieldPart || currentTarget instanceof AbstractShieldEntity) {
                        this.discard();
                    }
                }
            }
            this.setPos(this.position().add(this.getDeltaMovement()));
            this.spawnParticles();
        }
    }

    public @NotNull EntityDimensions getDimensions(@NotNull Pose p_19721_) {
        this.getBoundingBox();
        return EntityDimensions.scalable(this.getRadius() * 2.0F, 0.5F);
    }

    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> p_19729_) {
        if (DATA_RADIUS.equals(p_19729_)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(p_19729_);
    }

    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
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

    protected boolean canHitEntity(Entity entity) {
        return entity != this.getOwner() && super.canHitEntity(entity);
    }

    public void onAntiMagic(MagicData playerMagicData) {
        this.discard();
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("Damage", this.damage);
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.damage = pCompound.getFloat("Damage");
    }
}
