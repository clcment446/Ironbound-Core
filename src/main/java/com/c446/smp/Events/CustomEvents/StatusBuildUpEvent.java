package com.c446.smp.Events.CustomEvents;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;

public class StatusBuildUpEvent extends Event {









    public enum StatusList{
        MADNESS,
        BLEED,
        FROST,
        OVERCHARGED,
        RADIANT,
        HOLLOW
    }
}
