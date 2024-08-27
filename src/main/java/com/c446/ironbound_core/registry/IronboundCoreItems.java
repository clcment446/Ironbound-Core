package com.c446.ironbound_core.registry;

import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.HOLY_SPELL_POWER;
import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE;
import static net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED;
import static net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH;

import java.util.HashMap;
import java.util.UUID;

import com.c446.ironbound_core.Ironbound;
import com.c446.ironbound_core.config.IronboundCoreConfig;
import com.c446.ironbound_core.items.GenericAttributeItem;
import com.c446.ironbound_core.items.SimpleCuriosItem;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IronboundCoreItems{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ironbound.MOD_ID);
    public static final RegistryObject<Item> FOCUS_CHARM_1;
    public static final RegistryObject<Item> FOCUS_CHARM_2;
    public static final RegistryObject<Item> FOCUS_CHARM_3;
    public static final RegistryObject<Item> VITALITY_CHARM_1;
    public static final RegistryObject<Item> VITALITY_CHARM_2;
    public static final RegistryObject<Item> VITALITY_CHARM_3;
    public static final RegistryObject<Item> HOLY_SWORD;

    public static final HashMap<Attribute, AttributeModifier> FOCUS_CHARM_MAP1 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> FOCUS_CHARM_MAP2 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> FOCUS_CHARM_MAP3 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> VITALITY_CHARM_MAP1 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> VITALITY_CHARM_MAP2 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> VITALITY_CHARM_MAP3 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> LIFE_CURIOS_MAP = new HashMap<>();

    public static final HashMap<Attribute, ForgeConfigSpec.DoubleValue> HOLY_SWORD_MAP = new HashMap<>();
    public static final HashMap<Attribute, Double> HOLY_SWORD_DEFAULT_MAP = new HashMap<>();

    static {
        FOCUS_CHARM_MAP1.put(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get(), new AttributeModifier("focus_curio", 2, ADDITION));
        FOCUS_CHARM_MAP1.put(MAX_MANA.get(), new AttributeModifier("focus_curio", 15, ADDITION));
        FOCUS_CHARM_MAP2.put(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get(), new AttributeModifier("focus_curio", 4, ADDITION));
        FOCUS_CHARM_MAP2.put(MAX_MANA.get(), new AttributeModifier("focus_curio", 30, ADDITION));
        FOCUS_CHARM_MAP3.put(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get(), new AttributeModifier("focus_curio", 6, ADDITION));
        FOCUS_CHARM_MAP3.put(MAX_MANA.get(), new AttributeModifier("focus_curio", 45, ADDITION));

        VITALITY_CHARM_MAP1.put(IronboundCoreAttributes.VITALITY_ATTRIBUTE.get(), new AttributeModifier("vitality_curios", 2, ADDITION));
        VITALITY_CHARM_MAP1.put(MAX_HEALTH, new AttributeModifier("vitality_curios", 2, ADDITION));
        VITALITY_CHARM_MAP2.put(IronboundCoreAttributes.VITALITY_ATTRIBUTE.get(), new AttributeModifier("vitality_curios", 4, ADDITION));
        VITALITY_CHARM_MAP2.put(MAX_HEALTH, new AttributeModifier("vitality_curios", 4, ADDITION));
        VITALITY_CHARM_MAP3.put(IronboundCoreAttributes.VITALITY_ATTRIBUTE.get(), new AttributeModifier("vitality_curios", 6, ADDITION));
        VITALITY_CHARM_MAP3.put(MAX_HEALTH, new AttributeModifier("vitality_curios", 6, ADDITION));

        LIFE_CURIOS_MAP.put(IronboundCoreAttributes.VITALITY_ATTRIBUTE.get(), new AttributeModifier("life_curio", 10, ADDITION));
        LIFE_CURIOS_MAP.put(MAX_HEALTH, new AttributeModifier("life_curio", 10, ADDITION));
        FOCUS_CHARM_MAP3.put(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get(), new AttributeModifier("life_curio", 10, ADDITION));
        FOCUS_CHARM_MAP3.put(MAX_MANA.get(), new AttributeModifier("life_curio", 60, ADDITION));

        HOLY_SWORD_MAP.put(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get(), IronboundCoreConfig.HOLY_SWORD_FOCUS_BOOST);
        HOLY_SWORD_MAP.put(ATTACK_DAMAGE, IronboundCoreConfig.HOLY_SWORD_DAMAGE);
        HOLY_SWORD_MAP.put(ATTACK_SPEED, IronboundCoreConfig.HOLY_SWORD_ATTACK_SPED);
        HOLY_SWORD_MAP.put(HOLY_SPELL_POWER.get(), IronboundCoreConfig.HOLY_SWORD_HOLY_BOOST);
        HOLY_SWORD_MAP.put(ForgeMod.ENTITY_REACH.get(), IronboundCoreConfig.HOLY_SWORD_BONUS_REACH);

        HOLY_SWORD_DEFAULT_MAP.put(IronboundCoreAttributes.FOCUS_ATTRIBUTE.get(), 1.5);
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