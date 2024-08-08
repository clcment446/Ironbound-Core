package com.c446.smp.spells;

import com.c446.smp.IssSmpAddon;
import com.c446.smp.registry.ModRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Optional;

@AutoSpellConfig
public class SpellMoonlight extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(IssSmpAddon.MOD_ID, "moonlight_wave");
    public SpellMoonlight() {
        this.manaCostPerLevel = 150;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 1;
        this.baseManaCost = 100;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(SchoolRegistry.ELDRITCH_RESOURCE)
            .setMaxLevel(1)
            .setAllowCrafting(false)
            .setCooldownSeconds(15D)
            .build();

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundEvents.WARDEN_ROAR);
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.empty();
    }

    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (entity.isCrouching()){
            entity.addEffect(new MobEffectInstance(ModRegistry.PotionRegistry.MOONLIGHT_BLESSING.get(), (int)this.getSpellPower(spellLevel, entity) * 30,0,false,true));
        } else{
            //summon moonlight beam logic
        }
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.SELF_CAST_ANIMATION;
    }

    @Override
    public SchoolType getSchoolType() {
        return super.getSchoolType();
    }
}
