package com.c446.smp.capability.insight;

import net.minecraft.nbt.CompoundTag;

public class InsightCapability implements IInsightInterface{
    public int bonus_insight = 0;
    public boolean wither = false;
    public boolean dragon = false;
    public boolean dead_k = false;
    public boolean warden = false;
    public boolean heart = false;
    public int umbilical_cords = 0;



    public CompoundTag saveNBTData(CompoundTag nbt){
        nbt.putInt("insight", this.bonus_insight);
        nbt.putBoolean("wither", this.wither);
        nbt.putBoolean("dragon", this.dragon);
        nbt.putBoolean("dead_k", this.dead_k);
        nbt.putBoolean("warden", this.warden);
        nbt.putBoolean("heart", this.heart);
//        nbt.putInt("cords", this.umbilical_cords);
        return nbt;
    }

    public void loadNbt(CompoundTag nbt){
        this.bonus_insight = nbt.getInt("insight_bonus");
        this.wither = nbt.getBoolean("wither");
        this.dragon = nbt.getBoolean("dragon");
        this.dead_k = nbt.getBoolean("dead_k");
        this.warden = nbt.getBoolean("warden");
        this.heart = nbt.getBoolean("heart");
        //this.umbilical_cords = nbt.getInt("cords");
    }

}
