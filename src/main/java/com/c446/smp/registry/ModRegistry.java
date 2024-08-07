package com.c446.smp.registry;

import com.c446.smp.ISSAddon;
import com.c446.smp.effects.PublicEffect;
import com.c446.smp.spells.SpellMindFlay;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static com.c446.smp.registry.ModRegistry.AttributeRegistry.*;
import static com.c446.smp.registry.ModRegistry.PotionRegistry.EFFECTS;
import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.*;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

import static com.c446.smp.Util.ParticleUtil.rgbToInt;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_BASE;

public class ModRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ISSAddon.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ISSAddon.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ISSAddon.MOD_ID);

    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, ISSAddon.MOD_ID);

    public static class PotionRegistry {

        public static final DeferredRegister<MobEffect> EFFECTS;
        // BUFFS
        // ATTRIBUTES
        public static final RegistryObject<MobEffect> STRONG_MIND;
        public static final RegistryObject<MobEffect> STRONG_VITALITY;
        public static final RegistryObject<MobEffect> WEAK_MIND;
        public static final RegistryObject<MobEffect> WEAK_VITALITY;
        public static final RegistryObject<MobEffect> FROSTED_EFFECT;
        public static final RegistryObject<MobEffect> MADNESS;
        public static final RegistryObject<MobEffect> HOLLOW;
        public static final RegistryObject<MobEffect> RADIANT;
        public static final RegistryObject<MobEffect> OVERCHARGED;
        public static final RegistryObject<MobEffect> BLEED;

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
            EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ISSAddon.MOD_ID);

            UUID uuid = UUID.fromString("bb72a21d-3e49-4e8e-b81c-3bfa9cf746b0");

            BLEED = EFFECTS.register("bleed_mob_effect", () -> {
                return new PublicEffect();
            });
            

            OVERCHARGED = EFFECTS.register("overcharged_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.HARMFUL, rgbToInt(30, 100, 255)) {
                    public void addAttributeModifier(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        this.getAttributeModifiers().put(LIGHTNING_SPELL_POWER.get(), new AttributeModifier(uuid, this::getDescriptionId, getDamageBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        this.getAttributeModifiers().put(LIGHTNING_MAGIC_RESIST.get(), new AttributeModifier(uuid, this::getDescriptionId, getResReduction(pAmplifier), MULTIPLY_BASE));
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            HOLLOW = EFFECTS.register("hollow_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.BENEFICIAL, rgbToInt(255, 90, 255)) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        this.getAttributeModifiers().put((Attribute) ENDER_SPELL_POWER.get(), new AttributeModifier(uuid, this::getDescriptionId, getDamageBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        this.getAttributeModifiers().put((Attribute) FOCUS_ATTRIBUTE.get(), new AttributeModifier(uuid, this::getDescriptionId, getResBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            RADIANT = EFFECTS.register("radiant_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.BENEFICIAL, rgbToInt(255, 90, 255)) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        this.getAttributeModifiers().put((Attribute) HOLY_SPELL_POWER.get(), new AttributeModifier(uuid, this::getDescriptionId, getDamageBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        this.getAttributeModifiers().put((Attribute) UNDEAD_DAMAGE.get(), new AttributeModifier(uuid, this::getDescriptionId, getResBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            FROSTED_EFFECT = EFFECTS.register("frosted_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.HARMFUL, rgbToInt(0, 90, 255)) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        this.getAttributeModifiers().put((Attribute) SPELL_RESIST.get(), new AttributeModifier(uuid, this::getDescriptionId, getResReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        this.getAttributeModifiers().put((Attribute) FIRE_SPELL_POWER.get(), new AttributeModifier(uuid, this::getDescriptionId, getDamageReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            WEAK_MIND = EFFECTS.register("weak_mind_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.HARMFUL, rgbToInt(0, 70, 40)) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        AttributeModifier modifier = new AttributeModifier(UUID.fromString("bb72a21d-3e49-4e8e-b81c-3bfa9cf746b0"), this::getDescriptionId, getResReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE);
                        this.getAttributeModifiers().put((FOCUS_ATTRIBUTE.get()), modifier);
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            WEAK_VITALITY = EFFECTS.register("weak_vita_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.HARMFUL, 8080895) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        AttributeModifier modifier = new AttributeModifier(UUID.fromString("bb72a21d-3e49-4e8e-b81c-3bfa9cf746b0"), this::getDescriptionId, getResReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE);
                        this.getAttributeModifiers().put(VITALITY_ATTRIBUTE.get(), modifier);
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            STRONG_MIND = EFFECTS.register("strong_mind_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.BENEFICIAL, 8080895) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        AttributeModifier modifier = new AttributeModifier(UUID.fromString("bb72a21d-3e49-4e8e-b81c-3bfa9cf746b0"), this::getDescriptionId, (double) (0.1 + pAmplifier / 3), AttributeModifier.Operation.MULTIPLY_BASE);
                        Attribute attribute = FOCUS_ATTRIBUTE.get();
                        this.getAttributeModifiers().put((Attribute) (attribute), modifier);
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            STRONG_VITALITY = EFFECTS.register("strong_vita_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.BENEFICIAL, rgbToInt(50, 0, 0)) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        AttributeModifier modifier = new AttributeModifier(UUID.fromString("bb72a21d-3e49-4e8e-b81c-3bfa9cf746b0"), this::getDescriptionId, (double) (0.1 + pAmplifier / 3), MULTIPLY_BASE);
                        Attribute attribute = VITALITY_ATTRIBUTE.get();
                        this.getAttributeModifiers().put((Attribute) (attribute), modifier);
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }

                };
            });
            MADNESS = EFFECTS.register("madness_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.HARMFUL, rgbToInt(120, 90, 0)) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        AttributeModifier eldritchPowerMod = new AttributeModifier(UUID.fromString("bb72a21d-3e49-4e8e-b81c-3bfa9cf746b0"), this::getDescriptionId, (double) (0.5 + pAmplifier / 2), MULTIPLY_BASE);
                        AttributeModifier enderPowerMod = new AttributeModifier(UUID.fromString("bb72a21d-3e49-4e8e-b81c-3bfa9cf746b0"), this::getDescriptionId, (double) (0.5 + (pAmplifier / 2)), MULTIPLY_BASE);
                        AttributeModifier maxHealthMod = new AttributeModifier(UUID.fromString("bb72a21d-3e49-4e8e-b81c-3bfa9cf746b0"), this::getDescriptionId, (double) ((-0.1 + pAmplifier / 25)), MULTIPLY_BASE);
                        this.getAttributeModifiers().put((Attribute) ELDRITCH_SPELL_POWER.get(), eldritchPowerMod);
                        this.getAttributeModifiers().put((Attribute) ENDER_SPELL_POWER.get(), enderPowerMod);
                        this.getAttributeModifiers().put((Attribute) Attributes.MAX_HEALTH, maxHealthMod);
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
        }

    }
    @Mod.EventBusSubscriber(modid = ISSAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class AttributeRegistry {
        public static final HashMap<RegistryObject<Attribute>, UUID> UUIDS = new HashMap();
        public static final DeferredRegister<Attribute> ATTRIBUTES;

        public static RegistryObject<Attribute> VITALITY_ATTRIBUTE;
        public static RegistryObject<Attribute> FOCUS_ATTRIBUTE;
        public static RegistryObject<Attribute> UNDEAD_DAMAGE;


        static {
            ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ISSAddon.MOD_ID);

            VITALITY_ATTRIBUTE = registerAttribute("constitution", (id) ->
                    {
                        return (new RangedAttribute(id, 1.0, 0.0, 1024.0)).setSyncable(true);
                    }
                    , "a80e87d0-e18c-4d90-9c06-12e6cafa6844");
            FOCUS_ATTRIBUTE = registerAttribute("focus", (id) ->
                    {
                        return (new RangedAttribute(id, 1.0, 0.0, 1024.0)).setSyncable(true);
                    }
                    , "6b41f245-8d8d-4ba6-9128-8b3aa7ceef98");
        }

        public static RegistryObject<Attribute> registerAttribute(String name, Function<String, Attribute> attribute, String uuid) {
            return registerAttribute(name, attribute, UUID.fromString(uuid));
        }

        public static RegistryObject<Attribute> registerAttribute(String name, Function<String, Attribute> attribute, UUID uuid) {
            RegistryObject<Attribute> registryObject = ATTRIBUTES.register(name, () -> {
                return (Attribute) attribute.apply(name);
            });
            UUIDS.put(registryObject, uuid);
            return registryObject;
        }

        @SubscribeEvent
        public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
            event.getTypes().stream().filter((e) -> {
                return e == EntityType.PLAYER;
            }).forEach((e) -> {
                ATTRIBUTES.getEntries().forEach((v) -> {
                    event.add(e, (Attribute) v.get());
                });
            });
        }

        public static double getAttributeValue(LivingEntity entity, Attribute attribute) {
            AttributeInstance inst = entity.getAttribute(attribute);
            if (inst == null) return -Integer.MAX_VALUE;
            else return inst.getValue();
        }

    }

    public static void registerRegistries(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        ENTITIES.register(bus);
        EFFECTS.register(bus);
        SPELLS.register(bus);
        ATTRIBUTES.register(bus);
    }

    public static RegistryObject<AbstractSpell> registerSpell(@NotNull AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    public static final RegistryObject<AbstractSpell> SOUL_CRY = registerSpell(new SpellMindFlay());

}