package com.c446.smp.Events.Listeners;


import com.c446.smp.IssSmpAddon;
import com.c446.smp.capability.StatusAttacher;
import com.c446.smp.capability.StatusResistanceCap;
import com.c446.smp.registry.ModRegistry;
import com.c446.smp.spells.SpellMindFlay;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.capabilities.magic.PlayerMagicProvider;
import io.redspace.ironsspellbooks.spells.holy.HasteSpell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
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
    public static void onPlayerUpdate(){}

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

        oldPlayer.getCapability(STATUS_RESISTANCE_CAP).ifPresent(a->{

        });
    }


}
