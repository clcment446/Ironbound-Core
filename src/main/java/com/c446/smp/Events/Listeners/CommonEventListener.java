package com.c446.smp.Events.Listeners;


import com.c446.smp.IssSmpAddon;
import com.c446.smp.capability.StatusAttacher;
import com.c446.smp.capability.StatusResistanceCap;
import com.c446.smp.registry.ModRegistry;
import com.c446.smp.spells.SpellMindFlay;
import com.c446.smp.util.DamageUtil;
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
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.ItemModularHandheld;
import se.mickelus.tetra.module.ItemModuleMajor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.c446.smp.capability.StatusAttacher.StatusProvider.STATUS_RESISTANCE_CAP;


@Mod.EventBusSubscriber(modid = IssSmpAddon.MOD_ID)
public class CommonEventListener {


    @SubscribeEvent
    public static void SpellDamageReactions(SpellDamageEvent event) {
        LivingEntity entity = event.getEntity();
        LivingEntity source_entity = ((LivingEntity) event.getSpellDamageSource().getEntity());
        DamageSource damage_src = event.getSpellDamageSource().get();
        ServerLevel target_level = Objects.requireNonNull(Objects.requireNonNull(entity.level().getServer()).getLevel(entity.level().dimension()));
        if (damage_src.is(ISSDamageTypes.ICE_MAGIC)) {
            if (entity.hasEffect(ModRegistry.PotionRegistry.WET.get())) {
                entity.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                    c.setFrost_current(((int) event.getAmount() + c.getFrost_max()));
                });
            }
        }
        if (damage_src.is(ISSDamageTypes.LIGHTNING_MAGIC)) {
            entity.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                c.setOver_charged_current(((int) (c.getOver_charged_current() + event.getAmount())));
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
                c.setMadnessCurrent(((int) (event.getAmount() + c.getMadness_current())));
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
    public static void playerEffectAdded(MobEffectEvent event) {
        if (Objects.requireNonNull(event.getEffectInstance()).getEffect().equals(ModRegistry.PotionRegistry.MADNESS.get())) {
            event.getEntity().setHealth((float) (event.getEntity().getMaxHealth() - 0.2 * event.getEntity().getMaxHealth()));
            event.getEntity().getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(m -> {
                m.addMana(m.getMana() - m.getMana() * 0.3F);
            });
        }
    }


    public static int getRawStatusBuildupFrom(SpellOnCastEvent event) {
        return (int) (event.getSpellLevel() * SpellRegistry.getSpell(event.getSpellId()).getSpellPower(event.getSpellLevel(), event.getEntity()));
    }

    @SubscribeEvent
    public static void playerUseSchoolSpell(SpellOnCastEvent event) {
        AtomicInteger pureBuildUp = new AtomicInteger(getRawStatusBuildupFrom(event));
        SchoolType type = event.getSchoolType();
        Player p = event.getEntity();
        AbstractSpell spell = SpellRegistry.getSpell(event.getSpellId());

        if (type.equals(SchoolRegistry.ELDRITCH.get())) {
            p.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                if (spell instanceof SpellMindFlay) {
                    pureBuildUp.updateAndGet(v -> v * 2);
                }
                c.setMadnessCurrent(c.getMadness_current() + ((int) (pureBuildUp.get())));
            });
        }
        if (type.equals(SchoolRegistry.ENDER.get())) {
            p.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                if (spell instanceof io.redspace.ironsspellbooks.spells.ender.BlackHoleSpell) {
                    c.setTaint_current(((int) (c.getTaint_current() * pureBuildUp.get() * 2.5F)));
                }
                c.setTaint_current((c.getTaint_current() * pureBuildUp.get()));
            });
        }

        if (type.equals(SchoolRegistry.HOLY.get())) {
            p.getCapability(STATUS_RESISTANCE_CAP).ifPresent(c -> {
                if (spell instanceof HasteSpell) {
                    c.setFervor_current((((int) (c.getTaint_current() * pureBuildUp.get() * 1.5F))));
                }
                c.setFervor_current((c.getTaint_current() * pureBuildUp.get()));
            });
        }
    }

    @SubscribeEvent
    public static void testPlayerRain(LivingEvent.LivingTickEvent event) {
        if (Objects.requireNonNull(event.getEntity().level().getServer()).getTickCount() % 20 != 0) {
            return;
        }
        if (event.getEntity().isEyeInFluid(FluidTags.WATER) || event.getEntity().level().isRaining()) {
            event.getEntity().addEffect(new MobEffectInstance(ModRegistry.PotionRegistry.WET.get(), 40, 0, false, true));
        }
    }

    @SubscribeEvent
    public static void playerUseItems(LivingEntityUseItemEvent event) {
        if (event.getItem().getItem() instanceof ItemModularHandheld modularHandheld) {
            List<ItemModuleMajor> modules = Arrays.stream(modularHandheld.getMajorModules(event.getItem())).toList();
            for (ItemModuleMajor m : modules) {
                if (m.getImprovement(event.getItem(), "sword/murasama_imprv") != null) {
                    System.out.println("Murasama Blade detected with Entity " + event.getEntity().getDisplayName());
                }
            }
        }
    }

    @SubscribeEvent
    public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
//        System.out.println("attach Cap Entity triggered");
        if (event.getObject() instanceof Player player) {
            StatusAttacher.StatusProvider.Attach(event);
//            System.out.println("Arcane Level Capability Created");
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

        oldPlayer.getCapability(STATUS_RESISTANCE_CAP).ifPresent(a -> {

        });
    }
}
