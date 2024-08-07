package com.c446.smp.capability;

import com.c446.smp.registry.ModRegistry;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

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

    private int taint_max;
    private int taint_current;

    private int fervor_max;
    private int fervor_current;
    //public static
    public static final UUID focus_attribute_uuid = UUID.fromString("3d3349b1-02db-4f41-9a98-482f686047be");


    public void createResStuff(Player player) {
        this.madness_max = ((int) (20 * player.getAttributeValue(ModRegistry.AttributeRegistry.FOCUS_ATTRIBUTE.get())));
        this.taint_max = ((int) (15 * player.getAttributeValue(ModRegistry.AttributeRegistry.FOCUS_ATTRIBUTE.get())));
        this.fervor_max = ((int) (10 * player.getAttributeValue(ModRegistry.AttributeRegistry.FOCUS_ATTRIBUTE.get())));
        this.bleed_max = ((int) (5 * (player.getAttributeValue(ModRegistry.AttributeRegistry.VITALITY_ATTRIBUTE.get())+player.getHealth()/2)));
        this.frost_max = ((int) (3 * (player.getAttributeValue(ModRegistry.AttributeRegistry.VITALITY_ATTRIBUTE.get()) + player.getAttributeValue(ModRegistry.AttributeRegistry.FOCUS_ATTRIBUTE.get()))));




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

    public void setMadnessCurrent(int madness_current) {
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
        player.getCapability(StatusAttacher.StatusProvider.STATUS_RESISTANCE_CAP).ifPresent(a -> {
                a.bleed_max = (int) (player.getAttributeValue(ModRegistry.AttributeRegistry.VITALITY_ATTRIBUTE.get())*player.getAttributeValue(Attributes.MAX_HEALTH));
                a.frost_max = (int) (player.getAttributeValue(ModRegistry.AttributeRegistry.VITALITY_ATTRIBUTE.get())*player.getAttributeValue(Attributes.MAX_HEALTH));

                a.madness_max = (int) (player.getAttributeValue(ModRegistry.AttributeRegistry.FOCUS_ATTRIBUTE.get()) * player.getAttributeValue(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA.get())/100);
                a.soul_shattered_max = (int) (player.getAttributeValue(ModRegistry.AttributeRegistry.FOCUS_ATTRIBUTE.get()));

                }
        );
    }

    public int getTaint_max() {
        return taint_max;
    }

    public void setTaint_max(int taint_max) {
        this.taint_max = taint_max;
    }

    public int getTaint_current() {
        return taint_current;
    }

    public void setTaint_current(int taint_current) {
        this.taint_current = taint_current;
    }

    public int getOver_charged_max() {
        return over_charged_max;
    }

    public void setOver_charged_max(int over_charged_max) {
        this.over_charged_max = over_charged_max;
    }

    public int getOver_charged_current() {
        return over_charged_current;
    }

    public void setOver_charged_current(int over_charged_current) {
        this.over_charged_current = over_charged_current;
    }

    public int getFervor_max() {
        return fervor_max;
    }

    public void setFervor_max(int fervor_max) {
        this.fervor_max = fervor_max;
    }

    public int getFervor_current() {
        return fervor_current;
    }

    public void setFervor_current(int fervor_current) {
        this.fervor_current = fervor_current;
    }
}
