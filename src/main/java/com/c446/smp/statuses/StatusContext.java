//package com.c446.smp.statuses;
//
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//
//public class StatusContext {
//    public Entity getTarget() {
//        return target;
//    }
//
//    public void setTarget(Entity target) {
//        this.target = target;
//    }
//
//    public Entity getSource() {
//        return source;
//    }
//
//    public void setSource(Entity source) {
//        this.source = source;
//    }
//
//    public ServerLevel getLevel() {
//        return level;
//    }
//
//    public void setLevel(ServerLevel level) {
//        this.level = level;
//    }
//
//    public MobEffect getStatus() {
//        return effect;
//    }
//
//    public void MobEffect(MobEffect effect) {
//        this.effect = effect;
//    }
//
//    private Entity target;
//    private Entity source;
//    private ServerLevel level;
//    private MobEffect effect;
//
//    public StatusContext(Entity target, Entity source, ServerLevel level, IStatus status){
//        this.MobEffect();=status;
//        this.level=level;
//        this.source=source;
//        this.target=target;
//    }
//}
