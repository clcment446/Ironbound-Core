package com.c446.smp.events.hookers;

import com.c446.smp.IronBound;
import com.c446.smp.Util;
import com.c446.smp.capability.statuses.StatusAttacher;
import com.c446.smp.capability.statuses.StatusResistanceCap;
import com.c446.smp.events.mod_events.MobStatusTriggered;
import com.c446.smp.registry.ModRegistry;
import com.c446.smp.spells.SpellMindFlay;
import com.c446.smp.util.DamageUtil;
import com.c446.smp.util.StatusTypeHandler;
import com.c446.smp.util.StatusTypes;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.PlayerMagicProvider;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingBoss;
import io.redspace.ironsspellbooks.spells.ender.BlackHoleSpell;
import io.redspace.ironsspellbooks.spells.evocation.GustSpell;
import io.redspace.ironsspellbooks.spells.holy.HasteSpell;
import io.redspace.ironsspellbooks.spells.lightning.ChargeSpell;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.c446.smp.capability.insight.InsightAttacher.InsightProvider.INSIGHT_CAPABILITY_IDENTIFIER;
import static com.c446.smp.capability.statuses.StatusAttacher.StatusProvider.STATUS_RESISTANCE_CAP;

@Mod.EventBusSubscriber(modid = IronBound.MOD_ID)
public class CommonEventListener {
//    @SubscribeEvent
//    public static void counterspellFired();

    @SubscribeEvent
    public static void onServerStartup(ServerStartingEvent event) {
        event.getServer().sendSystemMessage(Component.literal("crimes against java were committed :^) "));
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        event.getServer().sendSystemMessage(Component.literal("Thank you for playing with our mod ;^)\n    -The ISS community addon team"));
    }

    @SubscribeEvent
    public static void SpellDamageReactions(SpellDamageEvent event) {
        /**
         * @Param event : the event that the hook will catch
         * This is what will handle the "main" reaction system, IE
         *
         * crimes against java were committed...
         */
        LivingEntity entity = event.getEntity();
        LivingEntity source_entity = ((LivingEntity) event.getSpellDamageSource().getEntity());
        assert source_entity != null;
        DamageSource damage_src = event.getSpellDamageSource().get();
        ServerLevel target_level = Objects.requireNonNull(Objects.requireNonNull(entity.level().getServer()).getLevel(entity.level().dimension()));
        if (damage_src.is(ISSDamageTypes.ICE_MAGIC)) {
            if (entity.hasEffect(ModRegistry.PotionRegistry.WET.get())) {
                entity.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                    c.setFrost_current(((int) event.getAmount() + c.getFrost_current()), ((Player) entity));
                });
            }
        }
        if (damage_src.is(ISSDamageTypes.LIGHTNING_MAGIC)) {
            entity.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                c.setOver_charged_current(((int) (c.getOver_charged_current() + event.getAmount())), ((Player) entity));
            });
            if (entity.hasEffect(ModRegistry.PotionRegistry.WET.get())) {
                event.setAmount(((int) (event.getAmount() * (1.5))));
            }
        }
        if (damage_src.is(ISSDamageTypes.FIRE_MAGIC)) {
            entity.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                if (entity.hasEffect(ModRegistry.PotionRegistry.WET.get())) {
                    event.setAmount((int) (event.getAmount() * 1.5));
                    entity.removeEffect(ModRegistry.PotionRegistry.WET.get());
                }
                if (entity.hasEffect(ModRegistry.PotionRegistry.OVERCHARGED.get())) {
                    target_level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, entity.getX(), entity.getY(), entity.getZ(), 3, 0, 0, 0, 1);
                    entity.invulnerableTime = 0;
                    DamageSources.applyDamage(entity, Objects.requireNonNull(entity.getEffect(ModRegistry.PotionRegistry.OVERCHARGED.get())).getAmplifier() * 3 + 10,
                            DamageUtil.source(entity.level(), DamageTypes.EXPLOSION, entity, event.getSpellDamageSource().getEntity()));
                }
            });
        }
        if (damage_src.is(ISSDamageTypes.ELDRITCH_MAGIC)) {
            entity.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                event.getSpellDamageSource().spell();
                c.setMadnessCurrent(((int) (event.getAmount() + c.getMadness_current())), ((Player) entity));
            });
        }
        if (damage_src.is(ISSDamageTypes.HOLY_MAGIC) && event.getEntity().getMobType() == (MobType.UNDEAD) && source_entity.getActiveEffects().contains(ModRegistry.PotionRegistry.FERVOR.get())) {
            MobEffectInstance instance = source_entity.getEffect(ModRegistry.PotionRegistry.FERVOR.get());
            event.setAmount(((float) (event.getAmount() * (1 + source_entity.getAttributeValue(ModRegistry.AttributeRegistry.UNDEAD_DAMAGE.get())) * (1 + 0.5 * (instance != null ? instance.getAmplifier() : 0)))));
        }

        // TO BE DONE
        if (damage_src.is(ISSDamageTypes.NATURE_MAGIC)) {
        }
        if (damage_src.is(ISSDamageTypes.BLOOD_MAGIC)) {
        }
        if (damage_src.is(ISSDamageTypes.EVOCATION_MAGIC)) {
        }
        if (damage_src.is(ISSDamageTypes.ENDER_MAGIC)) {
            entity.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                event.getSpellDamageSource().spell();
                c.setHollow_current(((int) (event.getAmount() + c.getHollowCurrent())), ((Player) entity));
            });
        }
    }

    @SubscribeEvent
    public static void gustHitEntities(SpellDamageEvent event) {
        if (!(event.getSpellDamageSource().spell() instanceof GustSpell)) {
            return;
        } else {
            LivingEntity target = event.getEntity();
            ServerLevel serverLevel = event.getEntity().level().getServer().getLevel(event.getEntity().level().dimension());
            assert serverLevel != null;
            List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, new AABB(target.position().subtract(3, 3, 3), target.position().add(3, 3, 3)));
            List<StatusTypes> statuses = new ArrayList<>();
            if (target.hasEffect(ModRegistry.PotionRegistry.WET.get())) {
                statuses.add(StatusTypes.WET);
            }
            if (target.hasEffect(ModRegistry.PotionRegistry.FROSTED_EFFECT.get())) {
                statuses.add(StatusTypes.FROST);
            }
            if (target.isOnFire()) {
                statuses.add(StatusTypes.FLAMABLE);
            }
            for (LivingEntity living : list) {
                for (StatusTypes t : statuses){
                    MobEffect mobeffect = StatusTypeHandler.TYPE_TO_EFFECT.get(t);
                    if (!(living.hasEffect(mobeffect))){
                        living.addEffect(new MobEffectInstance(mobeffect, 15,0,false,false));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void SpellCastReactions(SpellOnCastEvent event) {
        AtomicInteger pureBuildUp = new AtomicInteger(getSpellCasterBuildup(event));
        SchoolType type = event.getSchoolType();
        Player p = event.getEntity();
        AbstractSpell spell = SpellRegistry.getSpell(event.getSpellId());

        if (type.equals(SchoolRegistry.ELDRITCH.get())) {
            p.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                if (spell instanceof SpellMindFlay) {
                    pureBuildUp.updateAndGet(v -> v * 2);
                }
                c.setMadnessCurrent(c.getMadness_current() + ((int) (pureBuildUp.get())), event.getEntity());
            });
        }
        if (type.equals(SchoolRegistry.ENDER.get())) {
            p.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                if (spell instanceof BlackHoleSpell) {
                    c.setHollow_current(((int) (c.getHollowCurrent() * pureBuildUp.get() * 2.5F)), event.getEntity());
                }
                c.setHollow_current((c.getHollowCurrent() + pureBuildUp.get()), event.getEntity());
            });
        }
        if (type.equals(SchoolRegistry.HOLY.get())) {
            p.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                if (spell instanceof HasteSpell) {
                    c.setFervor_current((((int) (c.getHollowCurrent() * pureBuildUp.get() * 1.5F))), event.getEntity());
                }
                c.setFervor_current((c.getFervor_current() + pureBuildUp.get()), event.getEntity());
            });
        }
        if (spell instanceof ChargeSpell) {
            p.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                c.setOver_charged_current(((int) (c.getOver_charged_max() * 0.5 + 50)), event.getEntity());
            });
        }
    }

    public static int getSpellCasterBuildup(@NotNull SpellOnCastEvent event) {
        return (int) (event.getSpellLevel() * SpellRegistry.getSpell(event.getSpellId()).getSpellPower(event.getSpellLevel(), event.getEntity()));
    }

    @SubscribeEvent
    public static void playerEffectAdded(MobEffectEvent event) {
        if (Objects.requireNonNull(event.getEffectInstance()).getEffect().equals(ModRegistry.PotionRegistry.MADNESS.get())) {
            event.getEntity().setHealth((float) (event.getEntity().getMaxHealth() - 0.2 * event.getEntity().getMaxHealth()));
            event.getEntity().getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(m -> {
                m.addMana(m.getMana() - m.getMana() * 0.3F);
            });
        }
    }

    @SubscribeEvent
    public static void addWetEffect(LivingEvent.LivingTickEvent event) {
        if (Objects.requireNonNull(event.getEntity().level().getServer()).getTickCount() % 20 != 0) {
            return;
        }
        if (event.getEntity().isEyeInFluid(FluidTags.WATER) || event.getEntity().level().isRaining()) {
            event.getEntity().addEffect(new MobEffectInstance(ModRegistry.PotionRegistry.WET.get(), 40, 0, false, true));
        }
    }

    @SubscribeEvent
    public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity entity) {
            StatusAttacher.StatusProvider.Attach(event);
            entity.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                c.createResStuff(entity);
            });
        }
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(StatusResistanceCap.class);
    }


    @SubscribeEvent
    public static void playerDefeatBoss(net.minecraftforge.event.entity.living.LivingDeathEvent event){
        if(event.getEntity() instanceof Monster && event.getEntity().getLastAttacker() instanceof Player player){
            LivingEntity living = event.getEntity();
            AttributeModifier modifierToAppend;
            if (living instanceof EnderDragon){
                modifierToAppend = Util.InsightUtil.INSIGHT_DRAGON;
            }
            if (living instanceof WitherBoss){
                modifierToAppend = Util.InsightUtil.INSIGHT_WITHER;
            }
            if (living instanceof Warden){
                modifierToAppend = Util.InsightUtil.INSIGHT_WARDEN;
            }
            if (living instanceof DeadKingBoss){
                modifierToAppend = Util.InsightUtil.INSIGHT_DEAD_K;
            }
            player.getCapability(INSIGHT_CAPABILITY_IDENTIFIER).ifPresent(i->{

            });




        }

    }


    @SubscribeEvent
    public static void playerCloned(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        newPlayer.getCapability(STATUS_RESISTANCE_CAP).ifPresent(cap -> {
            cap.createResStuff(newPlayer);
        });
        final CompoundTag[] oldCapNBT = new CompoundTag[1];
                oldPlayer.getCapability(INSIGHT_CAPABILITY_IDENTIFIER).ifPresent(a-> oldCapNBT[0] = a.saveNBTData(new CompoundTag()));
        newPlayer.getCapability(INSIGHT_CAPABILITY_IDENTIFIER).ifPresent(n->{
            n.loadNbt(oldCapNBT[0]);
        });
    }

    @SubscribeEvent
    public static void onCalculatePlayerStatuses(MobStatusTriggered.Post postEvent) {
        Player player = postEvent.player;
        ArrayList<StatusTypes> list = postEvent.statusList;
        for (StatusTypes status : list) {

        }
    }

}
