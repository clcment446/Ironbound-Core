package com.c446.ironbound_core.registry;

import com.c446.ironbound_core.IronBound;
import com.c446.ironbound_core.ModConfig;
import com.c446.ironbound_core.effects.PublicEffect;
import com.c446.ironbound_core.items.GenericAttributeItem;
import com.c446.ironbound_core.items.SimpleCuriosItem;
import com.c446.ironbound_core.spells.SpellMindFlay;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

import static com.c446.ironbound_core.Util.ParticleUtil.rgbToInt;
import static com.c446.ironbound_core.registry.ModRegistry.AttributeRegistry.*;
import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.*;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_BASE;
import static net.minecraft.world.entity.ai.attributes.Attributes.*;

@Mod.EventBusSubscriber(modid = IronBound.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IronBound.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, IronBound.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IronBound.MOD_ID);
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(io.redspace.ironsspellbooks.api.registry.SpellRegistry.SPELL_REGISTRY_KEY, IronBound.MOD_ID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, IronBound.MOD_ID);

    public static void registerRegistries(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        ENTITIES.register(bus);
        EFFECTS.register(bus);
        SPELLS.register(bus);
        ATTRIBUTES.register(bus);
    }

    public static class EntityRegistry {
//        public static final RegistryObject<EntityType<MoonlightRayEntity>> MOONLIGHT_RAY_ENTITY;


//        static {
//            MOONLIGHT_RAY_ENTITY = ENTITIES.register("moonlight_ray", () -> {
//                return EntityType.Builder.of(MoonlightRayEntity::new, MobCategory.MISC)
//                        .sized(2.0F, 0.5F)
//                        .clientTrackingRange(64)
//                        .build((new ResourceLocation("irons_spellbooks", "moonlight_ray")).toString());});
//        }
//    }
    }

    public static class PotionRegistry {
        public static final RegistryObject<MobEffect> MOONLIGHT_BLESSING;
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

//            BLEED = EFFECTS.register("bleed_mob_effect" )

            WET = EFFECTS.register("wet_mob_effect", () -> {return new PublicEffect(MobEffectCategory.NEUTRAL, rgbToInt(0, 0, 125)) {};});

            FLAMMABLE = EFFECTS.register("flammable_mob_effect", () -> {return new PublicEffect(MobEffectCategory.HARMFUL, rgbToInt(0, 125, 0)) {};});

            OVERCHARGED = EFFECTS.register("overcharged_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.HARMFUL, rgbToInt(30, 100, 255)) {
                    public void addAttributeModifier(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        this.getAttributeModifiers().put(LIGHTNING_SPELL_POWER.get(), new AttributeModifier(uuid1, this::getDescriptionId, getDamageBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        this.getAttributeModifiers().put(LIGHTNING_MAGIC_RESIST.get(), new AttributeModifier(uuid1, this::getDescriptionId, getResReduction(pAmplifier), MULTIPLY_BASE));
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });

            MOONLIGHT_BLESSING = EFFECTS.register("moonlight_blessing_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.BENEFICIAL, rgbToInt(30, 100, 255)) {
                    public void addAttributeModifier(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        this.getAttributeModifiers().put(ICE_SPELL_POWER.get(), new AttributeModifier(uuid2, this::getDescriptionId, getDamageBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });

            HOLLOW = EFFECTS.register("hollow_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.BENEFICIAL, rgbToInt(255, 90, 255)) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        this.getAttributeModifiers().put((Attribute) ENDER_SPELL_POWER.get(), new AttributeModifier(uuid3, this::getDescriptionId, getDamageBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        this.getAttributeModifiers().put((Attribute) FOCUS_ATTRIBUTE.get(), new AttributeModifier(uuid3, this::getDescriptionId, getResBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            FERVOR = EFFECTS.register("radiant_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.BENEFICIAL, rgbToInt(255, 90, 255)) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        this.getAttributeModifiers().put((Attribute) HOLY_SPELL_POWER.get(), new AttributeModifier(uuid4, this::getDescriptionId, getDamageBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        this.getAttributeModifiers().put((Attribute) UNDEAD_DAMAGE.get(), new AttributeModifier(uuid4, this::getDescriptionId, getResBoost(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            FROSTED_EFFECT = EFFECTS.register("frosted_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.HARMFUL, rgbToInt(0, 90, 255)) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        this.getAttributeModifiers().put((Attribute) SPELL_RESIST.get(), new AttributeModifier(uuid5, this::getDescriptionId, getResReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        this.getAttributeModifiers().put((Attribute) FIRE_SPELL_POWER.get(), new AttributeModifier(uuid5, this::getDescriptionId, getDamageReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE));
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            WEAK_MIND = EFFECTS.register("weak_mind_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.HARMFUL, rgbToInt(0, 70, 40)) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        AttributeModifier modifier = new AttributeModifier(uuid6, this::getDescriptionId, getResReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE);
                        this.getAttributeModifiers().put((FOCUS_ATTRIBUTE.get()), modifier);
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            WEAK_VITALITY = EFFECTS.register("weak_vita_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.HARMFUL, 8080895) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        AttributeModifier modifier = new AttributeModifier(uuid7, this::getDescriptionId, getResReduction(pAmplifier), AttributeModifier.Operation.MULTIPLY_BASE);
                        this.getAttributeModifiers().put(VITALITY_ATTRIBUTE.get(), modifier);
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            STRONG_MIND = EFFECTS.register("strong_mind_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.BENEFICIAL, 8080895) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        AttributeModifier modifier = new AttributeModifier(uuid8, this::getDescriptionId, (double) (0.1 + pAmplifier / 3), AttributeModifier.Operation.MULTIPLY_BASE);
                        Attribute attribute = FOCUS_ATTRIBUTE.get();
                        this.getAttributeModifiers().put((Attribute) (attribute), modifier);
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }
                };
            });
            STRONG_VITALITY = EFFECTS.register("strong_vita_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.BENEFICIAL, rgbToInt(50, 0, 0)) {
                    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
                        AttributeModifier modifier = new AttributeModifier(uuid9, this::getDescriptionId, (double) (0.1 + pAmplifier / 3), MULTIPLY_BASE);
                        Attribute attribute = VITALITY_ATTRIBUTE.get();
                        this.getAttributeModifiers().put((Attribute) (attribute), modifier);
                        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
                    }

                };
            });
            MADNESS = EFFECTS.register("madness_mob_effect", () -> {
                return new PublicEffect(MobEffectCategory.HARMFUL, rgbToInt(120, 90, 0)) {
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

    public static class AttributeRegistry {
        public static double calcCounterSpellEvasionChance(Player caster, Player target, float coefficient) {
            /**
             * @Param caster : the caster
             * @Param target : the target
             * @Param coefficent : how steep the insight difference will be. Use something around 0.3 for best results.
             * @f
             */
            float insightDiff = (float) (target.getAttributeValue(INSIGHT_ATTRIBUTE.get()) - caster.getAttributeValue(INSIGHT_ATTRIBUTE.get()));
            return Math.max(1, Math.exp(coefficient * insightDiff));
        }

        public static final HashMap<RegistryObject<Attribute>, UUID> UUIDS = new HashMap();
        public static final DeferredRegister<Attribute> ATTRIBUTES;

        public static RegistryObject<Attribute> VITALITY_ATTRIBUTE;
        public static RegistryObject<Attribute> FOCUS_ATTRIBUTE;
        public static RegistryObject<Attribute> UNDEAD_DAMAGE;

        public static RegistryObject<Attribute> INSIGHT_ATTRIBUTE;
        public static RegistryObject<Attribute> LAPIS_FORTUNE;
        public static RegistryObject<Attribute> DIAMOND_FORTUNE;
        public static RegistryObject<Attribute> COAL_FORTUNE;
        public static RegistryObject<Attribute> REDSTONE_FORTUNE;
        public static RegistryObject<Attribute> IRON_FORTUNE;
        public static RegistryObject<Attribute> GOLD_FORTUNE;

        static {
            ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, IronBound.MOD_ID);

            VITALITY_ATTRIBUTE = registerAttribute("constitution", (id) ->
                    {
                        return (new RangedAttribute(id, 1.0, 0.0, 1024.0)).setSyncable(true);
                    }
                    , "a80e87d0-e18c-4d90-9c06-12e6cafa6844");
            FOCUS_ATTRIBUTE = registerAttribute("focus", (id) ->
                    {
                        return (new RangedAttribute(id, 5.0, 0.0, 1024.0)).setSyncable(true);
                    }
                    , "6b41f245-8d8d-4ba6-9128-8b3aa7ceef98");
            INSIGHT_ATTRIBUTE = registerAttribute("insight", (id) -> {
                        return (new RangedAttribute(id, 1, 0, 20)).setSyncable(true);
                    }
                    , "17f85bfd-47e3-40f5-bc4b-931056de2390");
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

    public static class SpellRegistry {
        public static RegistryObject<AbstractSpell> SOUL_CRY;
        public static RegistryObject<AbstractSpell> SPELL_MOONLIGHT;

        public static RegistryObject<AbstractSpell> registerSpell(@NotNull AbstractSpell spell) {
            return SPELLS.register(spell.getSpellName(), () -> spell);
        }

        static {
            registerSpell(new SpellMindFlay());
//            registerSpell(new SpellMoonlight());
        }
    }

    public static class ItemRegistry{
        public static final RegistryObject<Item> FOCUS_CHARM_1;
        public static final RegistryObject<Item> FOCUS_CHARM_2;
        public static final RegistryObject<Item> FOCUS_CHARM_3;
        public static final RegistryObject<Item> VITALITY_CHARM_1;
        public static final RegistryObject<Item> VITALITY_CHARM_2;
        public static final RegistryObject<Item> VITALITY_CHARM_3;
        public static final RegistryObject<Item> HOLY_SWORD;

        public static HashMap<Attribute, AttributeModifier> FOCUS_CHARM_MAP1 = new HashMap<>();
        public static HashMap<Attribute, AttributeModifier> FOCUS_CHARM_MAP2 = new HashMap<>();
        public static HashMap<Attribute, AttributeModifier> FOCUS_CHARM_MAP3 = new HashMap<>();
        public static HashMap<Attribute, AttributeModifier> VITALITY_CHARM_MAP1 = new HashMap<>();
        public static HashMap<Attribute, AttributeModifier> VITALITY_CHARM_MAP2 = new HashMap<>();
        public static HashMap<Attribute, AttributeModifier> VITALITY_CHARM_MAP3 = new HashMap<>();
        public static HashMap<Attribute, AttributeModifier> LIFE_CURIOS_MAP = new HashMap<>();

        public static HashMap<Attribute, ForgeConfigSpec.DoubleValue> HOLY_SWORD_MAP = new HashMap<>();
        public static HashMap<Attribute, Double> HOLY_SWORD_DEFAULT_MAP = new HashMap<>();



        static {
            FOCUS_CHARM_MAP1.put(FOCUS_ATTRIBUTE.get(), new AttributeModifier("focus_curio", 2, ADDITION));
            FOCUS_CHARM_MAP1.put(MAX_MANA.get(), new AttributeModifier("focus_curio", 15, ADDITION));
            FOCUS_CHARM_MAP2.put(FOCUS_ATTRIBUTE.get(), new AttributeModifier("focus_curio", 4, ADDITION));
            FOCUS_CHARM_MAP2.put(MAX_MANA.get(), new AttributeModifier("focus_curio", 30, ADDITION));
            FOCUS_CHARM_MAP3.put(FOCUS_ATTRIBUTE.get(), new AttributeModifier("focus_curio", 6, ADDITION));
            FOCUS_CHARM_MAP3.put(MAX_MANA.get(), new AttributeModifier("focus_curio", 45, ADDITION));

            VITALITY_CHARM_MAP1.put(VITALITY_ATTRIBUTE.get(), new AttributeModifier("vitality_curios", 2, ADDITION));
            VITALITY_CHARM_MAP1.put(MAX_HEALTH, new AttributeModifier("vitality_curios", 2, ADDITION));
            VITALITY_CHARM_MAP2.put(VITALITY_ATTRIBUTE.get(), new AttributeModifier("vitality_curios", 4, ADDITION));
            VITALITY_CHARM_MAP2.put(MAX_HEALTH, new AttributeModifier("vitality_curios", 4, ADDITION));
            VITALITY_CHARM_MAP3.put(VITALITY_ATTRIBUTE.get(), new AttributeModifier("vitality_curios", 6, ADDITION));
            VITALITY_CHARM_MAP3.put(MAX_HEALTH, new AttributeModifier("vitality_curios", 6, ADDITION));

            LIFE_CURIOS_MAP.put(VITALITY_ATTRIBUTE.get(), new AttributeModifier("life_curio", 10, ADDITION));
            LIFE_CURIOS_MAP.put(MAX_HEALTH, new AttributeModifier("life_curio", 10, ADDITION));
            FOCUS_CHARM_MAP3.put(FOCUS_ATTRIBUTE.get(), new AttributeModifier("life_curio", 10, ADDITION));
            FOCUS_CHARM_MAP3.put(MAX_MANA.get(), new AttributeModifier("life_curio", 60, ADDITION));

            HOLY_SWORD_MAP.put(FOCUS_ATTRIBUTE.get(), ModConfig.HOLY_SWORD_FOCUS_BOOST);
            HOLY_SWORD_MAP.put(ATTACK_DAMAGE, ModConfig.HOLY_SWORD_DAMAGE);
            HOLY_SWORD_MAP.put(ATTACK_SPEED, ModConfig.HOLY_SWORD_ATTACK_SPED);
            HOLY_SWORD_MAP.put(HOLY_SPELL_POWER.get(), ModConfig.HOLY_SWORD_HOLY_BOOST);
            HOLY_SWORD_MAP.put(ForgeMod.ENTITY_REACH.get(), ModConfig.HOLY_SWORD_BONUS_REACH);

            HOLY_SWORD_DEFAULT_MAP.put(FOCUS_ATTRIBUTE.get(), 1.5);
            HOLY_SWORD_DEFAULT_MAP.put(ForgeMod.ENTITY_REACH.get(), 1.5);
            HOLY_SWORD_DEFAULT_MAP.put(ATTACK_DAMAGE, 24.0);
            HOLY_SWORD_DEFAULT_MAP.put(ATTACK_SPEED, -1.5);
            HOLY_SWORD_DEFAULT_MAP.put(HOLY_SPELL_POWER.get(), 0.7);

            HOLY_SWORD = ITEMS.register("divine_claymore", ()->new GenericAttributeItem(new Item.Properties().rarity(Rarity.EPIC).fireResistant(), HOLY_SWORD_MAP, HOLY_SWORD_DEFAULT_MAP, UUID.fromString("ca7ffde6-b640-4774-add4-ca4f840c0ce7")));

            FOCUS_CHARM_1 = ITEMS.register("focus_charm_1", ()-> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.COMMON), FOCUS_CHARM_MAP1));
            FOCUS_CHARM_2 = ITEMS.register("focus_charm_2", ()-> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.UNCOMMON), FOCUS_CHARM_MAP2));
            FOCUS_CHARM_3 = ITEMS.register("focus_charm_3", ()-> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.RARE), FOCUS_CHARM_MAP3));

            VITALITY_CHARM_1 = ITEMS.register("vitality_charm_1", ()-> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.COMMON), VITALITY_CHARM_MAP1));
            VITALITY_CHARM_2 = ITEMS.register("vitality_charm_2", ()-> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.UNCOMMON), VITALITY_CHARM_MAP2));
            VITALITY_CHARM_3 = ITEMS.register("vitality_charm_3", ()-> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.RARE), VITALITY_CHARM_MAP3));

        }

    }
}