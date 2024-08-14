package com.c446.smp.events.mod_events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class StatusBuildUpEvent extends Event {
    Player player;
    StatusList status;
    public StatusBuildUpEvent(Player player, StatusList status){
        this.player = player;
        this.status = status;
    }

    public enum StatusList{
        MADNESS,
        BLEED,
        FROST,
        OVERCHARGED,
        RADIANT,
        FERVOR, SHATTERED, HOLLOW
    }
}
