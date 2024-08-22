package com.c446.smp.events.mod_events;

import com.c446.smp.util.StatusTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class StatusBuildUpEvent extends Event {
    Player player;
    StatusTypes status;

    public StatusBuildUpEvent(Player player, StatusTypes status) {
        this.player = player;
        this.status = status;
    }


}
