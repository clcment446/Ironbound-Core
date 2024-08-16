package com.c446.smp.events.hookers;

import com.c446.smp.IssSmpAddon;
import com.c446.smp.capability.StatusAttacher;
import com.c446.smp.capability.StatusResistanceCap;
import com.c446.smp.events.mod_events.MobStatusTriggered;
import com.c446.smp.registry.ModRegistry;
import com.c446.smp.spells.SpellMindFlay;
import com.c446.smp.util.DamageUtil;
import com.c446.smp.events.mod_events.StatusBuildUpEvent.StatusTypes;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.PlayerMagicProvider;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.spells.holy.HasteSpell;
import io.redspace.ironsspellbooks.spells.lightning.ChargeSpell;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.c446.smp.capability.StatusAttacher.StatusProvider.STATUS_RESISTANCE_CAP;

@Mod.EventBusSubscriber(modid = IssSmpAddon.MOD_ID)
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
        LivingEntity entity = event.getEntity();
        LivingEntity source_entity = ((LivingEntity) event.getSpellDamageSource().getEntity());
        DamageSource damage_src = event.getSpellDamageSource().get();
        ServerLevel target_level = Objects.requireNonNull(Objects.requireNonNull(entity.level().getServer()).getLevel(entity.level().dimension()));
        if (damage_src.is(ISSDamageTypes.ICE_MAGIC)) {
            if (entity.hasEffect(ModRegistry.PotionRegistry.WET.get())) {
                entity.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                    c.setFrost_current(((int) event.getAmount() + c.getFrost_max()), ((Player) entity));
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

        // TO BE DONE
        if (damage_src.is(ISSDamageTypes.NATURE_MAGIC)) {
        }
        if (damage_src.is(ISSDamageTypes.BLOOD_MAGIC)) {
        }
        if (damage_src.is(ISSDamageTypes.EVOCATION_MAGIC)) {
        }
        if (damage_src.is(ISSDamageTypes.ENDER_MAGIC)) {
        }
        if (damage_src.is(ISSDamageTypes.HOLY_MAGIC)) {
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
                if (spell instanceof io.redspace.ironsspellbooks.spells.ender.BlackHoleSpell) {
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
        if (event.getObject() instanceof Player player) {
            StatusAttacher.StatusProvider.Attach(event);
            player.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                c.createResStuff(player);
            });
        }
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(StatusResistanceCap.class);

//        System.out.println("Arcane Cap registered");
    }

    @SubscribeEvent
    public static void playerCloned(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        newPlayer.getCapability(STATUS_RESISTANCE_CAP).ifPresent(cap -> {
            cap.createResStuff(newPlayer);
        });
    }

    @SubscribeEvent
    public static void onCalculatePlayerStatuses(MobStatusTriggered.Post postEvent){
        Player player = postEvent.player;
        ArrayList<StatusTypes> list = postEvent.statusList;
        for (StatusTypes status : list){

        }
    }

}
