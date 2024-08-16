package com.c446.smp.util;

import com.c446.smp.events.mod_events.StatusBuildUpEvent;
import com.c446.smp.registry.ModRegistry;
import dev.shadowsoffire.attributeslib.api.ALObjects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

abstract public class StatusTypeHandler {
    public static HashMap<StatusBuildUpEvent.StatusTypes, MobEffect> TYPE_TO_EFFECT = new HashMap<>();

    static {
        TYPE_TO_EFFECT.put(StatusBuildUpEvent.StatusTypes.MADNESS, ModRegistry.PotionRegistry.MADNESS.get());
        TYPE_TO_EFFECT.put(StatusBuildUpEvent.StatusTypes.BLEED, ALObjects.MobEffects.BLEEDING.get());
        TYPE_TO_EFFECT.put(StatusBuildUpEvent.StatusTypes.FROST, ModRegistry.PotionRegistry.FROSTED_EFFECT.get());
        TYPE_TO_EFFECT.put(StatusBuildUpEvent.StatusTypes.OVERCHARGED, ModRegistry.PotionRegistry.OVERCHARGED.get());
        TYPE_TO_EFFECT.put(StatusBuildUpEvent.StatusTypes.FERVOR, ModRegistry.PotionRegistry.FERVOR.get());
        TYPE_TO_EFFECT.put(StatusBuildUpEvent.StatusTypes.HOLLOW, ModRegistry.PotionRegistry.HOLLOW.get());
        TYPE_TO_EFFECT.put(StatusBuildUpEvent.StatusTypes.WEAK_MIND, ModRegistry.PotionRegistry.WEAK_MIND.get());
        TYPE_TO_EFFECT.put(StatusBuildUpEvent.StatusTypes.WEAK_BODY, ModRegistry.PotionRegistry.WEAK_VITALITY.get());
    }

    public void HandlePlayer(ArrayList<StatusBuildUpEvent.StatusTypes> list, Player player){
        ArrayList<MobEffect> effectList= new ArrayList<>();
        for (StatusBuildUpEvent.StatusTypes e:list) {
            effectList.add(TYPE_TO_EFFECT.get(e));
        }
    }
}
