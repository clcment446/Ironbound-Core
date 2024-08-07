//package com.c446.smp.Events.CustomEvents;
//
//import com.c446.smp.statuses.StatusContext;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.eventbus.api.Event;
//import org.lwjgl.system.linux.Stat;
//
//public class StatusEvent extends Event {
//    private StatusContext context;
//
//
//    public StatusContext getContext() {
//        return context;
//    }
//
//    public void setContext(StatusContext context) {
//        this.context = context;
//    }
//
//    public StatusEvent(StatusContext context){
//        this.context = context;
//    }
//
//    public static class Pre extends StatusEvent {
//        @Override
//        public boolean isCancelable(){return true;}
//        public Pre(StatusContext context){
//            super(context);
//        }
//    }
//
//    public static class Post extends StatusEvent {
//        @Override
//        public boolean isCancelable(){return false;}
//
//        public Post(StatusContext context){
//            super(context);
//        }
//    }
//}
