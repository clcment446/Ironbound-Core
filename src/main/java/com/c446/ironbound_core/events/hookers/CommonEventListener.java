package com.c446.ironbound_core.events.hookers;

import com.c446.ironbound_core.Ironbound;
import com.c446.ironbound_core.capability.insight.InsightAttacher;
import com.c446.ironbound_core.capability.insight.InsightCapability;
import com.c446.ironbound_core.capability.temp_data_cap.DataAttacher;
import com.c446.ironbound_core.capability.temp_data_cap.DataCap;
import com.c446.ironbound_core.events.mod_events.MobStatusTriggeredEvent;
import com.c446.ironbound_core.registry.IronboundCoreAttributes;
import com.c446.ironbound_core.registry.IronboundCorePotions;
import com.c446.ironbound_core.spells.SpellMindFlay;
import com.c446.ironbound_core.util.DamageUtil;
import com.c446.ironbound_core.util.StatusTypeHandler;
import com.c446.ironbound_core.util.StatusTypes;
import com.c446.ironbound_core.util.Util;
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
import net.minecraft.advancements.critereon.ItemDurabilityTrigger;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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

import static com.c446.ironbound_core.capability.insight.InsightAttacher.InsightProvider.INSIGHT_CAPABILITY_IDENTIFIER;
import static com.c446.ironbound_core.capability.temp_data_cap.DataAttacher.DataProvider.DATA_CAP_CAPABILITY;

@Mod.EventBusSubscriber(modid = Ironbound.MOD_ID)
public class CommonEventListener {

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
        LivingEntity entity = event.getEntity();
        LivingEntity source_entity = ((LivingEntity) event.getSpellDamageSource().getEntity());
        assert source_entity != null;
        DamageSource damage_src = event.getSpellDamageSource().get();
        if (damage_src.is(ISSDamageTypes.ICE_MAGIC)) {
            if (entity.hasEffect(IronboundCorePotions.WET.get())) {
                entity.getCapability(DATA_CAP_CAPABILITY).ifPresent(c -> {
                    c.setFrostCurrent(((int) event.getAmount() + c.getFrostCurrent()), ((Player) entity));
                });
            }
        }
        if (damage_src.is(ISSDamageTypes.LIGHTNING_MAGIC)) {
            entity.getCapability(DATA_CAP_CAPABILITY).ifPresent(c -> {
                c.setOverChargedCurrent(((int) (c.getOverChargedCurrent() + event.getAmount())), ((Player) entity));
            });
            if (entity.hasEffect(IronboundCorePotions.WET.get())) {
                event.setAmount(((int) (event.getAmount() * (1.5))));
            }
        }
        if (damage_src.is(ISSDamageTypes.FIRE_MAGIC)) {
            entity.getCapability(DATA_CAP_CAPABILITY).ifPresent(c -> {
                if (entity.hasEffect(IronboundCorePotions.WET.get())) {
                    event.setAmount((int) (event.getAmount() * 1.5));
                    entity.removeEffect(IronboundCorePotions.WET.get());
                }
                if (entity.hasEffect(IronboundCorePotions.OVERCHARGED.get())) {
                	if(entity.level() instanceof ServerLevel serverLevel) {
                		serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, entity.getX(), entity.getY(), entity.getZ(), 3, 0, 0, 0, 1);
                	}
                    entity.invulnerableTime = 0;
                    DamageSources.applyDamage(entity, Objects.requireNonNull(entity.getEffect(IronboundCorePotions.OVERCHARGED.get())).getAmplifier() * 3 + 10,
                            DamageUtil.source(entity.level(), DamageTypes.EXPLOSION, entity, event.getSpellDamageSource().getEntity()));
                }
            });
        }
        if (damage_src.is(ISSDamageTypes.ELDRITCH_MAGIC)) {
            entity.getCapability(DATA_CAP_CAPABILITY).ifPresent(c -> {
                event.getSpellDamageSource().spell();
                c.setMadnessCurrent(((int) (event.getAmount() + c.getMadnessCurrent())), ((Player) entity));
            });
        }
        if (damage_src.is(ISSDamageTypes.HOLY_MAGIC) && event.getEntity().getMobType() == (MobType.UNDEAD) && source_entity.hasEffect(IronboundCorePotions.FERVOR.get())) {
            MobEffectInstance instance = source_entity.getEffect(IronboundCorePotions.FERVOR.get());
            event.setAmount(((float) (event.getAmount() * (1 + source_entity.getAttributeValue(IronboundCoreAttributes.UNDEAD_DAMAGE.get())) * (1 + 0.5 * (instance != null ? instance.getAmplifier() : 0)))));
        }

        // TO BE DONE
        if (damage_src.is(ISSDamageTypes.NATURE_MAGIC)) {
        }
        if (damage_src.is(ISSDamageTypes.BLOOD_MAGIC)) {
        }
        if (damage_src.is(ISSDamageTypes.EVOCATION_MAGIC)) {
        }
        if (damage_src.is(ISSDamageTypes.ENDER_MAGIC)) {
            entity.getCapability(DATA_CAP_CAPABILITY).ifPresent(c -> {
                event.getSpellDamageSource().spell();
                c.setHollowCurrent(((int) (event.getAmount() + c.getHollowCurrent())), ((Player) entity));
            });
        }
    }

    @SubscribeEvent
    public static void gustHitEntities(SpellDamageEvent event) {
        if (event.getSpellDamageSource().spell() instanceof GustSpell) {
            LivingEntity target = event.getEntity();
            ServerLevel serverLevel = event.getEntity().level().getServer().getLevel(event.getEntity().level().dimension());
            assert serverLevel != null;
            List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, new AABB(target.position().subtract(3, 3, 3), target.position().add(3, 3, 3)));
            List<StatusTypes> statuses = new ArrayList<>();
            if (target.hasEffect(IronboundCorePotions.WET.get())) {
                statuses.add(StatusTypes.WET);
            }
            if (target.hasEffect(IronboundCorePotions.FROSTED_EFFECT.get())) {
                statuses.add(StatusTypes.FROST);
            }
            if (target.isOnFire()) {
                statuses.add(StatusTypes.FLAMABLE);
            }
            for (LivingEntity living : list) {
                for (StatusTypes t : statuses) {
                    MobEffect mobeffect = StatusTypeHandler.TYPE_TO_EFFECT.get(t);
                    if (!(living.hasEffect(mobeffect))) {
                        living.addEffect(new MobEffectInstance(mobeffect, 15, 0, false, false));
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
            p.getCapability(DATA_CAP_CAPABILITY).ifPresent(c -> {
                if (spell instanceof SpellMindFlay) {
                    pureBuildUp.updateAndGet(v -> v * 2);
                }
                c.setMadnessCurrent(c.getMadnessCurrent() + pureBuildUp.get(), event.getEntity());
            });
        }
        if (type.equals(SchoolRegistry.ENDER.get())) {
            p.getCapability(DATA_CAP_CAPABILITY).ifPresent(c -> {
                if (spell instanceof BlackHoleSpell) {
                    c.setHollowCurrent(((int) (c.getHollowCurrent() * pureBuildUp.get() * 2.5F)), event.getEntity());
                }
                c.setHollowCurrent((c.getHollowCurrent() + pureBuildUp.get()), event.getEntity());
            });
        }
        if (type.equals(SchoolRegistry.HOLY.get())) {
            p.getCapability(DATA_CAP_CAPABILITY).ifPresent(c -> {
                if (spell instanceof HasteSpell) {
                    c.setFervorCurrent((((int) (c.getHollowCurrent() * pureBuildUp.get() * 1.5F))), event.getEntity());
                }
                c.setFervorCurrent((c.getFervorCurrent() + pureBuildUp.get()), event.getEntity());
            });
        }
        if (spell instanceof ChargeSpell) {
            p.getCapability(DATA_CAP_CAPABILITY).ifPresent(c -> {
                c.setOverChargedCurrent(((int) (c.getOverChargedMax() * 0.5 + 50)), event.getEntity());
            });
        }
    }

    public static int getSpellCasterBuildup(@NotNull SpellOnCastEvent event) {
        return (int) (event.getSpellLevel() * SpellRegistry.getSpell(event.getSpellId()).getSpellPower(event.getSpellLevel(), event.getEntity()));
    }

    @SubscribeEvent
    public static void playerEffectAdded(MobEffectEvent event) {
        if (Objects.requireNonNull(event.getEffectInstance()).getEffect().equals(IronboundCorePotions.MADNESS.get())) {
            event.getEntity().setHealth((float) (event.getEntity().getMaxHealth() - 0.2 * event.getEntity().getMaxHealth()));
            event.getEntity().getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(m -> {
                m.addMana(m.getMana() - m.getMana() * 0.3F);
            });
        }
    }

    @SubscribeEvent
    public static void addWetEffect(LivingEvent.LivingTickEvent event) {
        if (event.getEntity().tickCount % 20 == 0 && event.getEntity().isInWaterOrRain()) {
            event.getEntity().addEffect(new MobEffectInstance(IronboundCorePotions.WET.get(), 100, 0, false, true));
        }
    }

    @SubscribeEvent
    public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity entity) {
            DataAttacher.DataProvider.attach(event);
            entity.getCapability(DATA_CAP_CAPABILITY).ifPresent(c -> {
                c.createResistances(entity);
            });
            InsightAttacher.InsightProvider.attach(event);
        }
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(DataCap.class);
        event.register(InsightCapability.class);
    }

    @SubscribeEvent
    public static void playerDefeatBoss(net.minecraftforge.event.entity.living.LivingDeathEvent event) {
        if (event.getEntity() instanceof Monster && event.getEntity().getLastAttacker() instanceof Player player) {
            LivingEntity living = event.getEntity();
            AttributeModifier modifierToAppend;
            if (living instanceof EnderDragon) {
                modifierToAppend = Util.InsightUtil.INSIGHT_DRAGON;
            }
            if (living instanceof WitherBoss) {
                modifierToAppend = Util.InsightUtil.INSIGHT_WITHER;
            }
            if (living instanceof Warden) {
                modifierToAppend = Util.InsightUtil.INSIGHT_WARDEN;
            }
            if (living instanceof DeadKingBoss) {
                modifierToAppend = Util.InsightUtil.INSIGHT_DEAD_K;
            }
            player.getCapability(INSIGHT_CAPABILITY_IDENTIFIER).ifPresent(i -> {

            });
        }
    }

    @SubscribeEvent
    public static void playerCloned(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        oldPlayer.getCapability(DATA_CAP_CAPABILITY).ifPresent(c->{
            newPlayer.getCapability(DATA_CAP_CAPABILITY).ifPresent(cap -> {
                cap.createResistances(newPlayer);
            });
        });

        final CompoundTag[] oldCapNBT = new CompoundTag[1];
        oldPlayer.getCapability(INSIGHT_CAPABILITY_IDENTIFIER).ifPresent(a -> oldCapNBT[0] = a.save(new CompoundTag()));
        newPlayer.getCapability(INSIGHT_CAPABILITY_IDENTIFIER).ifPresent(n -> {
            n.load(oldCapNBT[0]);
        });
    }

    @SubscribeEvent
    public static void onCalculatePlayerStatuses(MobStatusTriggeredEvent.Post postEvent) {
        Player player = postEvent.player;
        ArrayList<StatusTypes> list = postEvent.statusList;

    }
}
