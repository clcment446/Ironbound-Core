package com.c446.ironbound_core.registry;

import static com.c446.ironbound_core.registry.IronboundCoreAttributes.FOCUS_ATTRIBUTE;
import static com.c446.ironbound_core.registry.IronboundCoreAttributes.UNDEAD_DAMAGE;
import static com.c446.ironbound_core.registry.IronboundCoreAttributes.VITALITY_ATTRIBUTE;
import static com.c446.ironbound_core.util.Util.ParticleUtil.rgbToInt;
import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.*;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_BASE;
import static net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH;

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.c446.ironbound_core.IronBound;
import com.c446.ironbound_core.effects.IronboundCoreEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IronboundCorePotions {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, IronBound.MOD_ID);

    public static final RegistryObject<MobEffect> MOONLIGHT_BLESSING;
    public static final RegistryObject<MobEffect> TIME_TWISTED;
    public static final RegistryObject<MobEffect> STRONG_MIND;
    public static final RegistryObject<MobEffect> STRONG_VITALITY;
    public static final RegistryObject<MobEffect> WEAK_MIND;
    public static final RegistryObject<MobEffect> WEAK_VITALITY;
    public static final RegistryObject<MobEffect> FROSTED_EFFECT;
    public static final RegistryObject<MobEffect> MADNESS;
    public static final RegistryObject<MobEffect> HOLLOW;
    public static final RegistryObject<MobEffect> FERVOR;
    public static final RegistryObject<MobEffect> OVERCHARGED;
    public static final RegistryObject<MobEffect> WET;
    public static final RegistryObject<MobEffect> FLAMMABLE;

    public static float getResBoost(int pAmpLevel) {
        return (0.2F + ((float) (pAmpLevel / 10)));
    }

    public static float getResReduction(int pAmpLevel) {
        return (1 - getResBoost(pAmpLevel));
    }

    public static float getDamageBoost(int pAmpLevel) {
        return (0.5F + pAmpLevel / 2);
    }

    public static float getDamageReduction(int pAmpLevel) {
        return (1 - getDamageBoost(pAmpLevel));
    }

    static {
        UUID uuid1 = UUID.fromString("0b72a21d-3e49-4e8e-b81c-3bfa9cf746b0");
        UUID uuid2 = UUID.fromString("1b72a21d-3e49-4e8e-b81c-3bfa9cf746b0");
        UUID uuid3 = UUID.fromString("2b72a21d-3e49-4e8e-b81c-3bfa9cf746b0");
        UUID uuid4 = UUID.fromString("3b72a21d-3e49-4e8e-b81c-3bfa9cf746b0");
        UUID uuid5 = UUID.fromString("4b72a21d-3e49-4e8e-b81c-3bfa9cf746b0");
        UUID uuid6 = UUID.fromString("5b72a21d-3e49-4e8e-b81c-3bfa9cf746b0");
        UUID uuid7 = UUID.fromString("672a21d-3e49-4e8e-b81c-3bfa9cf746b0");
        UUID uuid8 = UUID.fromString("772a21d-3e49-4e8e-b81c-3bfa9cf746b0");
        UUID uuid9 = UUID.fromString("872a21d-3e49-4e8e-b81c-3bfa9cf746b0");
        UUID uuid10 = UUID.fromString("972a21d-3e49-4e8e-b81c-3bfa9cf746b0");

        //BLEED = EFFECTS.register("bleed" )

        WET = EFFECTS.register("wet", () -> {
            return new IronboundCoreEffect(MobEffectCategory.NEUTRAL, rgbToInt(0, 0, 125)) {
            };
        });

        FLAMMABLE = EFFECTS.register("flammable", () -> {
            return new IronboundCoreEffect(MobEffectCategory.HARMFUL, rgbToInt(0, 125, 0)) {
            };
        });

        OVERCHARGED = EFFECTS.register("overcharged", () -> {
            return new IronboundCoreEffect(MobEffectCategory.HARMFUL, rgbToInt(30, 100, 255)) {
                public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                    this.getAttributeModifiers().put(LIGHTNING_SPELL_POWER.get(), new AttributeModifier(uuid1, this::getDescriptionId, getDamageBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                    this.getAttributeModifiers().put(LIGHTNING_MAGIC_RESIST.get(), new AttributeModifier(uuid1, this::getDescriptionId, getResReduction(pAmplifier), MULTIPLY_BASE));
                    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                }
            };
        });

        MOONLIGHT_BLESSING = EFFECTS.register("moonlight_blessing", () -> {
            return new IronboundCoreEffect(MobEffectCategory.BENEFICIAL, rgbToInt(30, 100, 255)) {
                public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                    this.getAttributeModifiers().put(ICE_SPELL_POWER.get(), new AttributeModifier(uuid2, this::getDescriptionId, getDamageBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                }
            };
        });

        TIME_TWISTED = EFFECTS.register("time_twist", () -> {
            return new IronboundCoreEffect(MobEffectCategory.HARMFUL, rgbToInt(170, 30, 230), List.of()) {
                public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier){
                    this.getAttributeModifiers().put(SPELL_RESIST.get(), new AttributeModifier(uuid10, this::getDescriptionId, -0.2 * Math.min((pAmplifier+1), 5), MULTIPLY_BASE));
                    this.getAttributeModifiers().put(COOLDOWN_REDUCTION.get(), new AttributeModifier(uuid10, this::getDescriptionId, -0.1    * Math.min((pAmplifier+1), 5), MULTIPLY_BASE));
                    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
               }
            };
        });

        HOLLOW = EFFECTS.register("hollow", () -> {
            return new IronboundCoreEffect(MobEffectCategory.BENEFICIAL, rgbToInt(255, 90, 255)) {
                public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                    this.getAttributeModifiers().put((Attribute) ENDER_SPELL_POWER.get(), new AttributeModifier(uuid3, this::getDescriptionId, getDamageBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                    this.getAttributeModifiers().put((Attribute) FOCUS_ATTRIBUTE.get(), new AttributeModifier(uuid3, this::getDescriptionId, getResBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                }
            };
        });
        FERVOR = EFFECTS.register("radiant", () -> {
            return new IronboundCoreEffect(MobEffectCategory.BENEFICIAL, rgbToInt(255, 90, 255)) {
                public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                    this.getAttributeModifiers().put((Attribute) HOLY_SPELL_POWER.get(), new AttributeModifier(uuid4, this::getDescriptionId, getDamageBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                    this.getAttributeModifiers().put((Attribute) UNDEAD_DAMAGE.get(), new AttributeModifier(uuid4, this::getDescriptionId, getResBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                }
            };
        });
        FROSTED_EFFECT = EFFECTS.register("frosted", () -> {
            return new IronboundCoreEffect(MobEffectCategory.HARMFUL, rgbToInt(0, 90, 255)) {
                public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                    this.getAttributeModifiers().put((Attribute) SPELL_RESIST.get(), new AttributeModifier(uuid5, this::getDescriptionId, getResReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                    this.getAttributeModifiers().put((Attribute) FIRE_SPELL_POWER.get(), new AttributeModifier(uuid5, this::getDescriptionId, getDamageReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                }
            };
        });
        WEAK_MIND = EFFECTS.register("weak_mind", () -> {
            return new IronboundCoreEffect(MobEffectCategory.HARMFUL, rgbToInt(0, 70, 40)) {
                public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                    AttributeModifier modifier = new AttributeModifier(uuid6, this::getDescriptionId, getResReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE);
                    this.getAttributeModifiers().put((FOCUS_ATTRIBUTE.get()), modifier);
                    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                }
            };
        });
        WEAK_VITALITY = EFFECTS.register("weak_vita", () -> {
            return new IronboundCoreEffect(MobEffectCategory.HARMFUL, 8080895) {
                public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                    AttributeModifier modifier = new AttributeModifier(uuid7, this::getDescriptionId, getResReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE);
                    this.getAttributeModifiers().put(VITALITY_ATTRIBUTE.get(), modifier);
                    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                }
            };
        });
        STRONG_MIND = EFFECTS.register("strong_mind", () -> {
            return new IronboundCoreEffect(MobEffectCategory.BENEFICIAL, 8080895) {
                public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                    AttributeModifier modifier = new AttributeModifier(uuid8, this::getDescriptionId, (double) (0.1 + pAmplifier / 3), AttributeModifier.Operation.MULTIPLY_BASE);
                    Attribute attribute = FOCUS_ATTRIBUTE.get();
                    this.getAttributeModifiers().put((Attribute) (attribute), modifier);
                    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                }
            };
        });
        STRONG_VITALITY = EFFECTS.register("strong_vita", () -> {
            return new IronboundCoreEffect(MobEffectCategory.BENEFICIAL, rgbToInt(50, 0, 0)) {
                public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                    AttributeModifier modifier = new AttributeModifier(uuid9, this::getDescriptionId, (double) (0.1 + pAmplifier / 3), MULTIPLY_BASE);
                    Attribute attribute = VITALITY_ATTRIBUTE.get();
                    this.getAttributeModifiers().put((Attribute) (attribute), modifier);
                    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                }

            };
        });
        MADNESS = EFFECTS.register("madness", () -> {
            return new IronboundCoreEffect(MobEffectCategory.HARMFUL, rgbToInt(120, 90, 0)) {
                public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                    AttributeModifier eldritchPowerMod = new AttributeModifier(uuid10, this::getDescriptionId, (double) (0.5 + pAmplifier / 2), MULTIPLY_BASE);
                    AttributeModifier enderPowerMod = new AttributeModifier(uuid10, this::getDescriptionId, (double) (0.5 + (pAmplifier / 2)), MULTIPLY_BASE);
                    AttributeModifier maxHealthMod = new AttributeModifier(uuid10, this::getDescriptionId, (double) ((-0.1 + pAmplifier / 25)), MULTIPLY_BASE);
                    this.getAttributeModifiers().put((Attribute) ELDRITCH_SPELL_POWER.get(), eldritchPowerMod);
                    this.getAttributeModifiers().put((Attribute) ENDER_SPELL_POWER.get(), enderPowerMod);
                    this.getAttributeModifiers().put((Attribute) MAX_HEALTH, maxHealthMod);
                    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                }
            };
        });
    }
}
