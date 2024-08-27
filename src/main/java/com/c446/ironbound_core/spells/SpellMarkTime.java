package com.c446.ironbound_core.spells;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.resources.ResourceLocation;

public class SpellMarkTime extends AbstractSpell {
    {
        this.castTime = 14;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return SchoolRegistry.ELDRITCH_RESOURCE;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return new DefaultConfig().setMaxLevel(3).setCooldownSeconds(120).setMinRarity(SpellRarity.LEGENDARY).build();
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public int getCastTime(int spellLevel) {
        return this.castTime;
    }
}
