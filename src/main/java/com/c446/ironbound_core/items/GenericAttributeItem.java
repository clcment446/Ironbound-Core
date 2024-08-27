package com.c446.ironbound_core.items;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.UUID;

public class GenericAttributeItem extends Item {
    HashMap<Attribute, ForgeConfigSpec.DoubleValue> map;
    HashMap<Attribute, Double> defaultMap;
    Properties p;
    UUID uuid;

    public GenericAttributeItem(Properties pProperties, HashMap<Attribute, ForgeConfigSpec.DoubleValue> attributeMap,HashMap<Attribute,Double> defaultMap, UUID attributeUUIDs) {
        super(pProperties);
        this.p = pProperties;
        this.map = attributeMap;
        this.uuid = attributeUUIDs;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> values = super.getAttributeModifiers(slot, stack);
        ItemAttributeHelper.handleConfig(map, defaultMap);


        return values;
    }
}
