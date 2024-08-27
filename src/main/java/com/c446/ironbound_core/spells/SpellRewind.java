package com.c446.ironbound_core.spells;

import com.c446.ironbound_core.capability.temp_data_cap.DataAttacher;
import com.c446.ironbound_core.effects.IronboundCoreEffect;
import com.c446.ironbound_core.registry.IronboundCorePotions;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.item.Scroll;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpellRewind extends AbstractSpell {
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
        return CastType.INSTANT;
    }

    @Override
    public int getCastTime(int spellLevel) {
        return this.castTime;
    }

    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return 2;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (entity instanceof Player player && !player.hasEffect(IronboundCorePotions.TIME_TWISTED.get())) {
            if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(this.getSpellId())) {
                playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(this.getSpellId(), spellLevel, this.getRecastCount(spellLevel, entity), this.getCastTime(spellLevel), castSource, (ICastDataSerializable) null), playerMagicData);
                player.getCapability(DataAttacher.DataProvider.DATA_CAP_CAPABILITY).ifPresent(c -> {
                    c.setRewindStoredPlayer(player);
                    c.rewind_begin = player.tickCount;
                });
            } else if (playerMagicData.getPlayerRecasts().hasRecastForSpell(this.getSpellId()) && playerMagicData.getPlayerRecasts().getRecastInstance(this.getSpellId()).getRemainingRecasts() == 1) {

                player.getCapability(DataAttacher.DataProvider.DATA_CAP_CAPABILITY).ifPresent(c -> {
                    Player oldPlayer = c.getRewindStoredPlayer();
                    if (oldPlayer.level() instanceof ServerLevel level1) {
                        player.changeDimension(level1);
                    }
                    player.setPos(oldPlayer.position());
                    player.setHealth(oldPlayer.getHealth());
                    player.setAbsorptionAmount(oldPlayer.getAbsorptionAmount());
                    player.getFoodData().setSaturation(oldPlayer.getFoodData().getSaturationLevel());
                    player.getFoodData().setFoodLevel(oldPlayer.getFoodData().getFoodLevel());
                    player.getFoodData().setExhaustion(oldPlayer.getFoodData().getExhaustionLevel());
                    player.setAbsorptionAmount(oldPlayer.getAbsorptionAmount());
                    player.setDeltaMovement(oldPlayer.getDeltaMovement());
                    List<MobEffectInstance> oldEffects = new ArrayList<>(player.getActiveEffects());
                    player.removeAllEffects();
                    for (MobEffectInstance instance : oldEffects) {
                        player.addEffect(instance);
                    }
                    player.addEffect(new MobEffectInstance(IronboundCorePotions.TIME_TWISTED.get(), Math.abs(player.tickCount - c.rewind_begin), Math.min(5, ((int) this.getSpellPower(spellLevel, entity)))));
                });
            }

        }
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult
            recastResult, ICastDataSerializable castDataSerializable) {
    }
}
