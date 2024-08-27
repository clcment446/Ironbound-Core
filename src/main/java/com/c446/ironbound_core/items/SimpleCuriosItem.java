package com.c446.ironbound_core.items;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.HashMap;
import java.util.UUID;

public class SimpleCuriosItem extends RegularItems implements ICurioItem {
    HashMap<Attribute, AttributeModifier> attributeAttributeModifierMap = new HashMap<>();

    public SimpleCuriosItem(Properties p, boolean showEnch, HashMap<Attribute, AttributeModifier> attributeAttributeModifierMap) {
        super(p, showEnch);
        this.attributeAttributeModifierMap = attributeAttributeModifierMap;
    }

    public SimpleCuriosItem(Properties p, HashMap<Attribute, AttributeModifier> attributeAttributeModifierMap) {
        super(p);
        this.attributeAttributeModifierMap = attributeAttributeModifierMap;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = ICurioItem.super.getAttributeModifiers(slotContext, uuid, stack);
        for (Attribute attr : attributeAttributeModifierMap.keySet()){
            attributes.put(attr, attributeAttributeModifierMap.get(attr));
        }

        return attributes;
    }
}
