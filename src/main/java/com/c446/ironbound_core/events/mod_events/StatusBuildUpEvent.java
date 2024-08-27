package com.c446.ironbound_core.events.mod_events;

import com.c446.ironbound_core.util.StatusTypes;
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
