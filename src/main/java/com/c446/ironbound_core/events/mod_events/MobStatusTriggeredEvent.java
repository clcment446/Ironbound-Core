package com.c446.ironbound_core.events.mod_events;

import com.c446.ironbound_core.util.StatusTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;

public class MobStatusTriggeredEvent extends Event {
    public Player player;
    public ArrayList<StatusTypes> statusList;

    public MobStatusTriggeredEvent(Player player, ArrayList<StatusTypes> statuses) {
        this.statusList = statuses;
        this.player = player;
    }

    public static class Pre extends MobStatusTriggeredEvent {
        public Pre(Player p, ArrayList<StatusTypes> statuses) {
            super(p, statuses);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    public static class Post extends MobStatusTriggeredEvent {
        public Post(Player p, ArrayList<StatusTypes> statuses) {
            super(p, statuses);
        }
    }
}
