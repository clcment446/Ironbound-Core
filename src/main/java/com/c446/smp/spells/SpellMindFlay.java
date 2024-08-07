package com.c446.smp.spells;

import com.c446.smp.ISSAddon;
import com.c446.smp.capability.StatusAttacher;
import com.c446.smp.registry.ModRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class SpellMindFlay extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(ISSAddon.MOD_ID, "soul_cry");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.codinglitchs_sorcery.radius", Utils.stringTruncation(getSpellPower(spellLevel, caster), 1))
        );
    }

    public SpellMindFlay() {
        this.manaCostPerLevel = 100;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 5;
        this.baseManaCost = 100;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.ELDRITCH_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(60D)
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
        float spellPower = getSpellPower(spellLevel, entity);
        HitResult targetResult = Utils.raycastForEntity(world, entity, 15.0F, true);
        if (targetResult.getType().equals(HitResult.Type.ENTITY)){
            LivingEntity living = (LivingEntity)((EntityHitResult) (targetResult)).getEntity();
            living.addEffect(new MobEffectInstance(ModRegistry.WEAK_MIND.get(), 1, 1));

        entity.getCapability(StatusAttacher.StatusProvider.INSTANCE).ifPresent(c->{

        });}

        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
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