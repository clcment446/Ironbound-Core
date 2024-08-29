package com.c446.ironbound_core.capability.temp_data_cap;

import com.c446.ironbound_core.capability.statuses.IStatusResistanceCap;
import com.c446.ironbound_core.events.mod_events.MobStatusTriggeredEvent;
import com.c446.ironbound_core.registry.IronboundCoreAttributes;
import com.c446.ironbound_core.util.StatusTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Function;

public class DataCap implements IStatusResistanceCap {
    public static final UUID FOCUS_ATTRIBUTE_UUID = UUID.fromString("3d3349b1-02db-4f41-9a98-482f686047be");
    public int rewind_begin;
    private int madnessMax;
    private int madnessCurrent;
    private int soulShatteredMax;
    private int soulShatteredCurrent;
    private int overChargedMax;
    private int overChargedCurrent;
    private int frostMax;
    private int frostCurrent;
    private int bleedMax;
    private int bleedCurrent;
    private int hollowMax;
    private int hollowCurrent;
    private int fervorMax;
    private int fervorCurrent;
    public CompoundTag storedData;
    public LivingEntity temp;

    public void createResistances(LivingEntity entity) {
        this.madnessMax = ((int) (20 * entity.getAttributeValue(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get())));
        this.hollowMax = ((int) (15 * entity.getAttributeValue(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get())));
        this.fervorMax = ((int) (10 * entity.getAttributeValue(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get())));
        this.soulShatteredMax = ((int) (10 * entity.getAttributeValue(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get())));
        this.bleedMax = ((int) (5 * (entity.getAttributeValue(IronboundCoreAttributes.VITALITY_ATTRIBUTE.get()) + entity.getHealth() / 2)));
        this.frostMax = ((int) (3 * (entity.getAttributeValue(IronboundCoreAttributes.VITALITY_ATTRIBUTE.get()) + entity.getAttributeValue(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get()))));
        this.overChargedMax = ((int) (3 * (entity.getAttributeValue(IronboundCoreAttributes.VITALITY_ATTRIBUTE.get()) + entity.getAttributeValue(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get()))));
    }

    public ArrayList<StatusTypes> checkStatus(Player player) {
        ArrayList<StatusTypes> list = new ArrayList<StatusTypes>();
        if (hollowCurrent > hollowMax) {
            list.add(StatusTypes.HOLLOW);
        }
        if (fervorCurrent > fervorMax) {
            list.add(StatusTypes.FERVOR);
        }
        if (madnessCurrent > madnessMax) {
            list.add(StatusTypes.MADNESS);
        }
        if (bleedCurrent > bleedMax) {
            list.add(StatusTypes.BLEED);
        }
        if (frostCurrent > frostMax) {
            list.add(StatusTypes.FROST);
        }
        if (soulShatteredCurrent > soulShatteredMax) {
            list.add(StatusTypes.WEAK_MIND);
        }
        if (overChargedCurrent > overChargedMax) {
            list.add(StatusTypes.OVERCHARGED);
        }
        MinecraftForge.EVENT_BUS.post(new MobStatusTriggeredEvent.Pre(player, list));
        return list;
    }

    public int getMadnessMax() {
        return madnessMax;
    }

    public void setMadnessMax(int madnessMax) {
        this.madnessMax = madnessMax;
    }

    public int getMadnessCurrent() {
        return madnessCurrent;
    }

    public void setMadnessCurrent(int madnessCurrent, Player entity) {
        System.out.println("madness " + this.madnessCurrent + " -> " + madnessCurrent);
        this.madnessCurrent = madnessCurrent;
    }

    public int getFrostMax() {
        return frostMax;
    }

    public void setFrostMax(int frostMax) {
        this.frostMax = frostMax;
    }

    public int getFrostCurrent() {
        return frostCurrent;
    }

    public void setFrostCurrent(int frostCurrent, Player entity) {
        System.out.println("frost " + this.frostCurrent + " -> " + frostCurrent);
        this.frostCurrent = frostCurrent;
    }

    public int getBleedMax() {
        return bleedMax;
    }

    public void setBleedMax(int bleedMax) {
        this.bleedMax = bleedMax;
    }

    public int getBleedCurrent() {
        return bleedCurrent;
    }

    public void setBleedCurrent(int bleedCurrent) {
        System.out.println("bleed " + this.bleedCurrent + " -> " + bleedCurrent);
        this.bleedCurrent = bleedCurrent;
    }

    public int getSoulShatteredCurrent() {
        return soulShatteredCurrent;
    }

    public void setSoulShatteredCurrent(int soulShatteredCurrent) {
        this.soulShatteredCurrent = soulShatteredCurrent;
    }

    public int getSoulShatteredMax() {
        return soulShatteredMax;
    }

    public void setSoulShatteredMax(int soulShatteredMax) {
        System.out.println("shattered soul " + this.soulShatteredCurrent + " -> " + soulShatteredMax);
        this.soulShatteredMax = soulShatteredMax;
    }

    public int getHollowMax() {
        return hollowMax;
    }

    public void setHollowMax(int hollowMax) {
        this.hollowMax = hollowMax;
    }

    public int getHollowCurrent() {
        return hollowCurrent;
    }

    public void setHollowCurrent(int hollowCurrent, Player entity) {
        System.out.println("hollow " + this.hollowCurrent + " -> " + hollowCurrent);
        this.hollowCurrent = hollowCurrent;
    }

    public int getOverChargedMax() {
        return overChargedMax;
    }

    public void setOverChargedMax(int overChargedMax) {
        this.overChargedMax = overChargedMax;
    }

    public int getOverChargedCurrent() {
        return overChargedCurrent;
    }

    public void setOverChargedCurrent(int overChargedCurrent, Player entity) {
        System.out.println("overcharged " + this.overChargedCurrent + " -> " + overChargedCurrent);
        this.overChargedCurrent = overChargedCurrent;
    }

    public int getFervorMax() {
        return fervorMax;
    }

    public void setFervorMax(int fervorMax) {
        this.fervorMax = fervorMax;
    }

    public int getFervorCurrent() {
        return fervorCurrent;
    }

    public void setFervorCurrent(int fervorCurrent, Player entity) {
        System.out.println("fervor " + this.fervorCurrent + " -> " + fervorCurrent);
        this.fervorCurrent = fervorCurrent;
    }


    public void setNbtFromEntity(@NotNull LivingEntity living) {
        this.storedData = living.serializeNBT();
        System.out.println("ENTITY ACCEPTED INTO CAP");
    }

    public CompoundTag getNbt() {
        System.out.println("ENTITY GIVEN");
        return storedData;
    }

    public Entity getEntity(Level level) {

        Entity entity = EntityType.loadEntityRecursive(this.getNbt(), level, Function.identity());
        if (entity instanceof Player){
            return entity;
        }
        return null;
    }
}
