package com.c446.smp.capability;

import com.c446.smp.registry.ModRegistry;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class StatusResistanceCap implements IStatusResistanceCap {
    private int madness_max;
    private int madness_current;

    private int soul_shattered_current;
    private int soul_shattered_max;

    private int over_charged_max;
    private int over_charged_current;

    private int frost_max;
    private int frost_current;

    private int bleed_max;
    private int bleed_current;



    public void createResStuff(Player player) {

    }


    public int getMadness_max() {
        return madness_max;
    }

    public void setMadness_max(int madness_max) {
        this.madness_max = madness_max;
    }

    public int getMadness_current() {
        return madness_current;
    }

    public void setMadness_current(int madness_current) {
        this.madness_current = madness_current;
    }

    public int getFrost_max() {
        return frost_max;
    }

    public void setFrost_max(int frost_max) {
        this.frost_max = frost_max;
    }

    public int getFrost_current() {
        return frost_current;
    }

    public void setFrost_current(int frost_current) {
        this.frost_current = frost_current;
    }

    public int getBleed_max() {
        return bleed_max;
    }

    public void setBleed_max(int bleed_max) {
        this.bleed_max = bleed_max;
    }

    public int getBleed_current() {
        return bleed_current;
    }

    public void setBleed_current(int bleed_current) {
        this.bleed_current = bleed_current;
    }

    public int getSoul_shattered_current() {
        return soul_shattered_current;
    }

    public void setSoul_shattered_current(int soul_shattered_current) {
        this.soul_shattered_current = soul_shattered_current;
    }

    public int getSoul_shattered_max() {
        return soul_shattered_max;
    }

    public void setSoul_shattered_max(int soul_shattered_max) {
        this.soul_shattered_max = soul_shattered_max;
    }

    public void generatePlayerResistances(Player player) {
        player.getCapability(StatusAttacher.StatusProvider.INSTANCE).ifPresent(a -> {
                a.bleed_max = (int) (player.getAttributeValue(ModRegistry.AttributeRegistry.VITALITY_ATTRIBUTE.get())*player.getAttributeValue(Attributes.MAX_HEALTH));
                a.frost_max = (int) (player.getAttributeValue(ModRegistry.AttributeRegistry.VITALITY_ATTRIBUTE.get())*player.getAttributeValue(Attributes.MAX_HEALTH));

                a.madness_max = (int) (player.getAttributeValue(ModRegistry.AttributeRegistry.FOCUS_ATTRIBUTE.get()) * player.getAttributeValue(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA.get())/100);
                a.soul_shattered_max = (int) (player.getAttributeValue(ModRegistry.AttributeRegistry.FOCUS_ATTRIBUTE.get()));

                }
        );
    }
}
