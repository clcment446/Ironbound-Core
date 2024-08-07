package com.c446.smp.Events.Listeners;


import com.c446.smp.ISSAddon;
import com.c446.smp.registry.ModRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.PlayerMagicProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid= ISSAddon.MOD_ID)
public class CommonEventListener {


    @SubscribeEvent
    public static void playerEffectAdded(MobEffectEvent event){
        MobEffect effect = Objects.requireNonNull(event.getEffectInstance()).getEffect();
        String madness = ModRegistry.PotionRegistry.MADNESS.get().getDescriptionId();
        String frost = ModRegistry.PotionRegistry.FROSTED_EFFECT.get().getDescriptionId();
        String bleed = ModRegistry.PotionRegistry.BLEED.get().getDescriptionId();



        switch (effect.getDescriptionId()){
            case () {}

        }


        if(Objects.requireNonNull(event.getEffectInstance()).getEffect().equals(ModRegistry.PotionRegistry.MADNESS.get())){
            event.getEntity().setHealth((float) (event.getEntity().getMaxHealth() - 0.2 * event.getEntity().getMaxHealth()));
            event.getEntity().getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(m->{
                m.addMana(m.getMana() - m.getMana() * 0.3F);
            });
        }
    }

    @SubscribeEvent
}
