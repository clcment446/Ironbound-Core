package com.c446.smp.statuses;

import com.c446.smp.Events.CustomEvents.StatusEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.EventBus;

public interface IStatus {
    String name = "";
    IStatus instance = null;


    default void broadcastEventPre(StatusEvent.Pre event, EventBus bus){
        bus.post(event);
    }

    default void broadcastEventPost(StatusEvent.Post event, EventBus bus){
        bus.post(event);
    }

    default void statusOnPlayer(StatusEvent.Post event){}
    default void statusOnLiving(StatusEvent.Post event){}
    default void statusOnEntity(StatusEvent.Post event){}
}
