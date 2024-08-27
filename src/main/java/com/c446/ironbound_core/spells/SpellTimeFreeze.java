package com.c446.ironbound_core.spells;

import java.util.List;

import com.c446.ironbound_core.Ironbound;
import com.min01.entitytimer.TimerUtil;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@AutoSpellConfig
public class SpellTimeFreeze extends AbstractSpell
{
    private final ResourceLocation spellId = new ResourceLocation(Ironbound.MOD_ID, "time_freeze");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(100)
            .build();
    
	@Override
	public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
		List<Entity> list = level.getEntitiesOfClass(Entity.class, entity.getBoundingBox().inflate(10));
		list.removeIf(t -> t == entity);
		list.forEach(t -> {
			TimerUtil.setTickrate(t, 0);
		});
		super.onCast(level, spellLevel, entity, castSource, playerMagicData);
	}
	
    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.instant_cast", Utils.stringTruncation(getSpellPower(spellLevel, caster), 1))
        );
    }
	
	@Override
	public int getCastTime(int spellLevel) {
		return 5;
	}
	
	@Override
	public CastType getCastType() {
		return CastType.LONG;
	}

	@Override
	public DefaultConfig getDefaultConfig() {
		return this.defaultConfig;
	}

	@Override
	public ResourceLocation getSpellResource() {
		return this.spellId;
	}
}
