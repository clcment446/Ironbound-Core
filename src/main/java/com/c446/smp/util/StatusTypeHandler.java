package com.c446.smp.util;

import com.c446.smp.events.mod_events.StatusBuildUpEvent;
import com.c446.smp.registry.ModRegistry;
import dev.shadowsoffire.attributeslib.api.ALObjects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

abstract public class StatusTypeHandler {
    public static HashMap<StatusTypes, MobEffect> TYPE_TO_EFFECT = new HashMap<>();

    static {
        TYPE_TO_EFFECT.put(StatusTypes.MADNESS, ModRegistry.PotionRegistry.MADNESS.get());
        TYPE_TO_EFFECT.put(StatusTypes.BLEED, ALObjects.MobEffects.BLEEDING.get());
        TYPE_TO_EFFECT.put(StatusTypes.FROST, ModRegistry.PotionRegistry.FROSTED_EFFECT.get());
        TYPE_TO_EFFECT.put(StatusTypes.OVERCHARGED, ModRegistry.PotionRegistry.OVERCHARGED.get());
        TYPE_TO_EFFECT.put(StatusTypes.FERVOR, ModRegistry.PotionRegistry.FERVOR.get());
        TYPE_TO_EFFECT.put(StatusTypes.HOLLOW, ModRegistry.PotionRegistry.HOLLOW.get());
        TYPE_TO_EFFECT.put(StatusTypes.WEAK_MIND, ModRegistry.PotionRegistry.WEAK_MIND.get());
        TYPE_TO_EFFECT.put(StatusTypes.WEAK_BODY, ModRegistry.PotionRegistry.WEAK_VITALITY.get());
        TYPE_TO_EFFECT.put(StatusTypes.WET, ModRegistry.PotionRegistry.WET.get());
        TYPE_TO_EFFECT.put(StatusTypes.FLAMABLE, ModRegistry.PotionRegistry.FLAMMABLE.get());

    }

    public static ArrayList<MobEffect> handleEntity(ArrayList<StatusTypes> list, LivingEntity player){
        ArrayList<MobEffect> effectList= new ArrayList<>();
        for (StatusTypes e:list) {
            effectList.add(TYPE_TO_EFFECT.get(e));
        }
    return effectList;
    }
}
