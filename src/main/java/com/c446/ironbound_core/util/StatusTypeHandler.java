package com.c446.ironbound_core.util;

import com.c446.ironbound_core.registry.IronboundCorePotions;
import dev.shadowsoffire.attributeslib.api.ALObjects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class StatusTypeHandler {
    public static final HashMap<StatusTypes, MobEffect> TYPE_TO_EFFECT = new HashMap<>();

    static {
        TYPE_TO_EFFECT.put(StatusTypes.MADNESS, IronboundCorePotions.MADNESS.get());
        TYPE_TO_EFFECT.put(StatusTypes.BLEED, ALObjects.MobEffects.BLEEDING.get());
        TYPE_TO_EFFECT.put(StatusTypes.FROST, IronboundCorePotions.FROSTED_EFFECT.get());
        TYPE_TO_EFFECT.put(StatusTypes.OVERCHARGED, IronboundCorePotions.OVERCHARGED.get());
        TYPE_TO_EFFECT.put(StatusTypes.FERVOR, IronboundCorePotions.FERVOR.get());
        TYPE_TO_EFFECT.put(StatusTypes.HOLLOW, IronboundCorePotions.HOLLOW.get());
        TYPE_TO_EFFECT.put(StatusTypes.WEAK_MIND, IronboundCorePotions.WEAK_MIND.get());
        TYPE_TO_EFFECT.put(StatusTypes.WEAK_BODY, IronboundCorePotions.WEAK_VITALITY.get());
        TYPE_TO_EFFECT.put(StatusTypes.WET, IronboundCorePotions.WET.get());
        TYPE_TO_EFFECT.put(StatusTypes.FLAMABLE, IronboundCorePotions.FLAMMABLE.get());
    }

    public static ArrayList<MobEffect> handleEntity(ArrayList<StatusTypes> list, LivingEntity player) {
        ArrayList<MobEffect> effectList = new ArrayList<>();
        for (StatusTypes e : list) {
            effectList.add(TYPE_TO_EFFECT.get(e));
        }
        return effectList;
    }
}
