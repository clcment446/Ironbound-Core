package com.c446.ironbound_core.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PublicEffect extends MobEffect {
    public final List<ItemStack> curativeItems;

    public PublicEffect(MobEffectCategory p_i50391_1_, int p_i50391_2_) {
        this(p_i50391_1_, p_i50391_2_, (List) null);
    }

    public PublicEffect(MobEffectCategory type, int color, List<ItemStack> curativeItems) {
        super(type, color);
        this.curativeItems = curativeItems;
    }

    public List<ItemStack> getCurativeItems() {
        return this.curativeItems != null ? this.curativeItems : super.getCurativeItems();
    }
}