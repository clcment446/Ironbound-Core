package com.c446.ironbound_core;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class Util {
    public static class ParticleUtil{
        public static int rgbToInt (int red, int green, int blue){
            return ((red << 16) | (green << 8) | blue);
        }
    }

    public static class InsightUtil{
        public static AttributeModifier INSIGHT_DEAD_K = new AttributeModifier(UUID.fromString("48cd21fb-80ac-4350-8b6e-b9f2e7bda538"),"insight_king", 4, AttributeModifier.Operation.ADDITION);
        public static AttributeModifier INSIGHT_DRAGON = new AttributeModifier(UUID.fromString("4c13a6f0-429c-4e45-9b67-ba88c7d73ee5"),"insight_dragon", 3, AttributeModifier.Operation.ADDITION);
        public static AttributeModifier INSIGHT_WITHER = new AttributeModifier(UUID.fromString("b37a01e2-d1da-4a97-b8d4-f8887fe22328"),"insight_wither", 2, AttributeModifier.Operation.ADDITION);
        public static AttributeModifier INSIGHT_WARDEN = new AttributeModifier(UUID.fromString("7b323189-018b-4eb1-93d8-758bff64563c"), "insight_warden", 1, AttributeModifier.Operation.ADDITION);



    }
}
