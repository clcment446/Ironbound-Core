package com.c446.smp.events.mod_events;

import com.c446.smp.registry.ModRegistry;
import dev.shadowsoffire.attributeslib.api.ALObjects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

import java.util.HashMap;

public class StatusBuildUpEvent extends Event {
    Player player;
    StatusTypes status;

    public StatusBuildUpEvent(Player player, StatusTypes status) {
        this.player = player;
        this.status = status;
    }

    public enum StatusTypes {
        MADNESS,
        BLEED,
        FROST,
        OVERCHARGED,
        WEAK_BODY,
        FERVOR,
        WEAK_MIND,
        HOLLOW
    }


}
