package com.c446.ironbound_core.spells;

import com.c446.ironbound_core.Ironbound;
import com.c446.ironbound_core.capability.temp_data_cap.DataAttacher;
import com.c446.ironbound_core.registry.IronboundCorePotions;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@AutoSpellConfig
public class SpellRewind extends AbstractSpell {

    @Override
    public DefaultConfig getDefaultConfig() {
        return new DefaultConfig()
                .setMaxLevel(3)
                .setCooldownSeconds(120)
                .setMinRarity(SpellRarity.LEGENDARY)
                .setMinRarity(SpellRarity.LEGENDARY)
                .setAllowCrafting(false)
                .setSchoolResource(SchoolRegistry.ELDRITCH_RESOURCE)
                .build();
    }

    private final ResourceLocation spellId = new ResourceLocation(Ironbound.MOD_ID, "rewind");

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
    public void onCast(Level level, int spellLevel, LivingEntity entity1, CastSource castSource, MagicData playerMagicData) {
        System.out.println("REWIND CASTED");
        if (!entity1.hasEffect(IronboundCorePotions.TIME_TWISTED.get()) && !playerMagicData.getPlayerRecasts().hasRecastForSpell(this.getSpellId())) {
            System.out.println("REWIND ACCEPTED");
            System.out.println("PLAYER HAS NO RECASTS");
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(this.getSpellId(), spellLevel, 2, this.getRecastDuration(spellLevel), castSource, (ICastDataSerializable) null), playerMagicData);
            entity1.getCapability(DataAttacher.DataProvider.DATA_CAP_CAPABILITY).ifPresent(c -> {
                System.out.println("PLAYER TEMP DATA ACCESSED");
                c.storedData = entity1.serializeNBT();
                c.temp = entity1;
                //System.out.println(entity1 + "STORED SUCCESSFULLY");
                System.out.println("PUSHING REWIND START");
            });
        } else if (playerMagicData.getPlayerRecasts().hasRecastForSpell(this.getSpellId()) && playerMagicData.getPlayerRecasts().getRecastInstance(this.getSpellId()).getRemainingRecasts() == 1) {
            System.out.println("PLAYER HAS RECAST");
            entity1.getCapability(DataAttacher.DataProvider.DATA_CAP_CAPABILITY).ifPresent(c -> {
                System.out.println("CAP ACCESSED");

                //@NotNull LivingEntity oldEntity = (LivingEntity) EntityType.loadEntityRecursive(c.storedData, level, Function.identity());

                LivingEntity oldEntity = c.temp;
                // System.out.println("OLD PLAYER FOUND : " + oldEntity.toString());
                if (entity1 instanceof Player player && oldEntity instanceof Player old) {
                    if (oldEntity.level() instanceof ServerLevel level1 && entity1.level() != level1) {
                        entity1.changeDimension(level1);
                    }
                    System.out.println("DOING OLD PLAYER STUFF");
                    player.setPos(oldEntity.getPosition(0));
                    player.setHealth(old.getHealth());
                    player.setAbsorptionAmount(old.getAbsorptionAmount());
                    player.getFoodData().setSaturation(old.getFoodData().getSaturationLevel());
                    player.getFoodData().setFoodLevel(old.getFoodData().getFoodLevel());
                    player.getFoodData().setExhaustion(old.getFoodData().getExhaustionLevel());
                    player.setAbsorptionAmount(old.getAbsorptionAmount());
                    player.setDeltaMovement(old.getDeltaMovement());
                    List<MobEffectInstance> oldEffects = new ArrayList<>(player.getActiveEffects());
                    player.removeAllEffects();
                    for (MobEffectInstance instance : oldEffects) {
                        player.addEffect(instance);
                    }
                    player.addEffect(new MobEffectInstance(IronboundCorePotions.TIME_TWISTED.get(), Math.abs(entity1.tickCount - c.rewind_begin), Math.min(5, ((int) this.getSpellPower(spellLevel, entity1)))));
                } else {
                    System.out.println("DOING OLD PLAYER STUFF");
                    entity1.setPos(oldEntity.position());
                    entity1.setHealth(oldEntity.getHealth());
                    entity1.setAbsorptionAmount(oldEntity.getAbsorptionAmount());
                    entity1.setAbsorptionAmount(oldEntity.getAbsorptionAmount());
                    entity1.setDeltaMovement(oldEntity.getDeltaMovement());
                    List<MobEffectInstance> oldEffects = new ArrayList<>(entity1.getActiveEffects());
                    entity1.removeAllEffects();
                    for (MobEffectInstance instance : oldEffects) {
                        entity1.addEffect(instance);
                    }
                    entity1.addEffect(new MobEffectInstance(IronboundCorePotions.TIME_TWISTED.get(), Math.abs(entity1.tickCount - c.rewind_begin), Math.min(5, ((int) this.getSpellPower(spellLevel, entity1)))));

                }
            });
        }

        super.onCast(level, spellLevel, entity1, castSource, playerMagicData);
    }

    private int getRecastDuration(int spellLevel) {
        return 180 + 90*(spellLevel + 2);
    }

    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult
            recastResult, ICastDataSerializable castDataSerializable) {
    }

    @Override
    public ResourceLocation getSpellResource() {
        return this.spellId;
    }
}
