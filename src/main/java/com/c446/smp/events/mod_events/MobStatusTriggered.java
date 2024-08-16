package com.c446.smp.events.mod_events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;

public class MobStatusTriggered extends Event {
    public  Player player;
    public  ArrayList<StatusBuildUpEvent.StatusTypes> statusList;

    public MobStatusTriggered(Player player, ArrayList<StatusBuildUpEvent.StatusTypes> statuses) {
        this.statusList = statuses;
        this.player = player;
    }

    public static class Pre extends MobStatusTriggered {
        public Pre(Player p, ArrayList<StatusBuildUpEvent.StatusTypes> statuses) {
            super(p, statuses);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    public static class Post extends MobStatusTriggered {
        public Post(Player p, ArrayList<StatusBuildUpEvent.StatusTypes> statuses) {
            super(p, statuses);
        }
    }
}
