package com.c446.smp;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {

    public static final ForgeConfigSpec.DoubleValue FROST_EFFECT_SPELL_DEFENSE_SHRED;
    public static final ForgeConfigSpec.DoubleValue FROST_EFFECT_PHYS_DEFENSE_SHRED;
    public static final ForgeConfigSpec.DoubleValue FROST_EFFECT_HP_PCT_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue WEAK_VITA_SHRED;
    public static final ForgeConfigSpec.DoubleValue WEAK_SOUL_SHRED;
    public static final ForgeConfigSpec.DoubleValue MADNESS_EFFECT_ELDR_SPELL_BONUS;
    public static final ForgeConfigSpec.DoubleValue MADNESS_EFFECT_MANA_LOSS_PCT;
    public static final ForgeConfigSpec.DoubleValue MADNESS_EFFECT_HP_LOSS_PCT;



    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        FROST_EFFECT_SPELL_DEFENSE_SHRED = SERVER_BUILDER.comment("how much the frost effect shreds spell defense").defineInRange("frost_effect_spell_def_shred", 0.1,0,100000);
        FROST_EFFECT_PHYS_DEFENSE_SHRED = SERVER_BUILDER.comment("how much the frost effect shreds spell defense").defineInRange("frost_effect_spell_phy_shred", 0.1,0,100000);
        FROST_EFFECT_HP_PCT_DAMAGE = SERVER_BUILDER.comment("base % of hp lost when frost triggers").defineInRange("frost_effect_hp_pct_loss",0.2,0,1000);

        WEAK_SOUL_SHRED = SERVER_BUILDER.comment("how much the weak soul effect shreds focus").defineInRange("weak_mind_focus_shred", 0.1,0,100000);

        WEAK_VITA_SHRED = SERVER_BUILDER.comment("how much the weak vitality effect shreds vitality").defineInRange("weak_mind_focus_shred", 0.1,0,100000);

        MADNESS_EFFECT_ELDR_SPELL_BONUS = SERVER_BUILDER.comment("bonus eldr spell damage granted by the madness status effect").defineInRange("madness_effect_eldr_bonus",0.1,-1000, 1000);
        MADNESS_EFFECT_HP_LOSS_PCT = SERVER_BUILDER.comment("madness hp pct damage").defineInRange("madness_hp_damage",0.2,0,0);
        MADNESS_EFFECT_MANA_LOSS_PCT = SERVER_BUILDER.comment("madness mana pct loss").defineInRange("madness_mana_loss",0.2,0,0);




        ForgeConfigSpec SERVER;


    }
}
