package com.c446.ironbound_core.registry;

import com.c446.ironbound_core.Ironbound;
import com.c446.ironbound_core.config.IronboundCoreConfig;
import com.c446.ironbound_core.items.GenericAttributeItem;
import com.c446.ironbound_core.items.GenericTrickWeapon;
import com.c446.ironbound_core.items.SimpleCuriosItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Supplier;

import static com.c446.ironbound_core.Ironbound.MOD_ID;
import static com.c446.ironbound_core.registry.IronboundCoreAttributes.FOCUS_ATTRIBUTE;
import static com.c446.ironbound_core.registry.IronboundCoreAttributes.VITALITY_ATTRIBUTE;
import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.HOLY_SPELL_POWER;
import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.Attributes.*;
import static net.minecraftforge.common.ForgeMod.ENTITY_REACH;



public class IronboundCoreItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Item> FOCUS_CHARM_1;
    public static final RegistryObject<Item> FOCUS_CHARM_2;
    public static final RegistryObject<Item> FOCUS_CHARM_3;
    public static final RegistryObject<Item> VITALITY_CHARM_1;
    public static final RegistryObject<Item> VITALITY_CHARM_2;
    public static final RegistryObject<Item> VITALITY_CHARM_3;
    public static final RegistryObject<Item> HOLY_SWORD;
    public static final RegistryObject<Item> DARK_CLAYMORE;

    public static final HashMap<Attribute, AttributeModifier> FOCUS_CHARM_MAP1 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> FOCUS_CHARM_MAP2 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> FOCUS_CHARM_MAP3 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> VITALITY_CHARM_MAP1 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> VITALITY_CHARM_MAP2 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> VITALITY_CHARM_MAP3 = new HashMap<>();
    public static final HashMap<Attribute, AttributeModifier> LIFE_CURIOS_MAP = new HashMap<>();

    public static final HashMap<Attribute, ForgeConfigSpec.DoubleValue> HOLY_SWORD_MAP = new HashMap<>();
    public static final HashMap<Attribute, Double> HOLY_SWORD_DEFAULT_MAP = new HashMap<>();

    public static final HashMap<Attribute, Double> DARK_CLAYMORE_ON = new HashMap<>();
    public static final HashMap<Attribute, Double> DARK_CLAYMORE_OFF = new HashMap<>();

    public static final HashMap<Attribute, ForgeConfigSpec.DoubleValue> DARK_CLAYMORE_ON_CFG = new HashMap<>();
    public static final HashMap<Attribute, ForgeConfigSpec.DoubleValue> DARK_CLAYMORE_OFF_CFG = new HashMap<>();



    static {
        HOLY_SWORD = ITEMS.register("divine_claymore", () -> new GenericAttributeItem(new Item.Properties().rarity(Rarity.EPIC).fireResistant(), HOLY_SWORD_MAP, HOLY_SWORD_DEFAULT_MAP, UUID.fromString("ca7ffde6-b640-4774-add4-ca4f840c0ce7")));

        DARK_CLAYMORE = ITEMS.register("dark_claymore", ()-> new GenericTrickWeapon(new Item.Properties().fireResistant().setNoRepair().rarity(Rarity.EPIC), new ResourceLocation[]{new ResourceLocation(MOD_ID, "textures/item/dark_claymore_on"), new ResourceLocation(MOD_ID, "textures/item/dark_claymore_off")}, DARK_CLAYMORE_ON_CFG, DARK_CLAYMORE_ON, DARK_CLAYMORE_OFF_CFG,DARK_CLAYMORE_OFF, UUID.randomUUID()));

        FOCUS_CHARM_1 = ITEMS.register("focus_charm_1", () -> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.COMMON), FOCUS_CHARM_MAP1));
        FOCUS_CHARM_2 = ITEMS.register("focus_charm_2", () -> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.UNCOMMON), FOCUS_CHARM_MAP2));
        FOCUS_CHARM_3 = ITEMS.register("focus_charm_3", () -> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.RARE), FOCUS_CHARM_MAP3));

        VITALITY_CHARM_1 = ITEMS.register("vitality_charm_1", () -> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.COMMON), VITALITY_CHARM_MAP1));
        VITALITY_CHARM_2 = ITEMS.register("vitality_charm_2", () -> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.UNCOMMON), VITALITY_CHARM_MAP2));
        VITALITY_CHARM_3 = ITEMS.register("vitality_charm_3", () -> new SimpleCuriosItem(new Item.Properties().rarity(Rarity.RARE), VITALITY_CHARM_MAP3));
    }

    public static void setupAttributeMap() {
        put(FOCUS_CHARM_MAP1, () -> FOCUS_ATTRIBUTE.get(), new AttributeModifier("focus_curio", 2, ADDITION));
        put(FOCUS_CHARM_MAP1, () -> MAX_MANA.get(), new AttributeModifier("focus_curio", 15, ADDITION));
        put(FOCUS_CHARM_MAP2, () -> FOCUS_ATTRIBUTE.get(), new AttributeModifier("focus_curio", 4, ADDITION));
        put(FOCUS_CHARM_MAP2, () -> MAX_MANA.get(), new AttributeModifier("focus_curio", 30, ADDITION));
        put(FOCUS_CHARM_MAP3, () -> FOCUS_ATTRIBUTE.get(), new AttributeModifier("focus_curio", 6, ADDITION));
        put(FOCUS_CHARM_MAP3, () -> MAX_MANA.get(), new AttributeModifier("focus_curio", 45, ADDITION));

        put(VITALITY_CHARM_MAP1, VITALITY_ATTRIBUTE::get, new AttributeModifier("vitality_curios", 2, ADDITION));
        put(VITALITY_CHARM_MAP1, () -> MAX_HEALTH, new AttributeModifier("vitality_curios", 2, ADDITION));
        put(VITALITY_CHARM_MAP2, () -> VITALITY_ATTRIBUTE.get(), new AttributeModifier("vitality_curios", 4, ADDITION));
        put(VITALITY_CHARM_MAP2, () -> MAX_HEALTH, new AttributeModifier("vitality_curios", 4, ADDITION));
        put(VITALITY_CHARM_MAP3, () -> VITALITY_ATTRIBUTE.get(), new AttributeModifier("vitality_curios", 6, ADDITION));
        put(VITALITY_CHARM_MAP3, () -> MAX_HEALTH, new AttributeModifier("vitality_curios", 6, ADDITION));

        put(LIFE_CURIOS_MAP, () -> VITALITY_ATTRIBUTE.get(), new AttributeModifier("life_curio", 10, ADDITION));
        put(LIFE_CURIOS_MAP, () -> MAX_HEALTH, new AttributeModifier("life_curio", 10, ADDITION));
        put(FOCUS_CHARM_MAP3, () -> FOCUS_ATTRIBUTE.get(), new AttributeModifier("life_curio", 10, ADDITION));
        put(FOCUS_CHARM_MAP3, () -> MAX_MANA.get(), new AttributeModifier("life_curio", 60, ADDITION));

        put(HOLY_SWORD_MAP, () -> FOCUS_ATTRIBUTE.get(), IronboundCoreConfig.HOLY_SWORD_FOCUS_BOOST);
        put(HOLY_SWORD_MAP, () -> ATTACK_DAMAGE, IronboundCoreConfig.HOLY_SWORD_DAMAGE);
        put(HOLY_SWORD_MAP, () -> ATTACK_SPEED, IronboundCoreConfig.HOLY_SWORD_ATTACK_SPED);
        put(HOLY_SWORD_MAP, () -> HOLY_SPELL_POWER.get(), IronboundCoreConfig.HOLY_SWORD_HOLY_BOOST);
        put(HOLY_SWORD_MAP, () -> ENTITY_REACH.get(), IronboundCoreConfig.HOLY_SWORD_BONUS_REACH);

        put(HOLY_SWORD_DEFAULT_MAP, () -> FOCUS_ATTRIBUTE.get(), 1.5);
        put(HOLY_SWORD_DEFAULT_MAP, () -> ENTITY_REACH.get(), 1.5);
        put(HOLY_SWORD_DEFAULT_MAP, () -> ATTACK_DAMAGE, 24.0);
        put(HOLY_SWORD_DEFAULT_MAP, () -> ATTACK_SPEED, -1.5);
        put(HOLY_SWORD_DEFAULT_MAP, () -> HOLY_SPELL_POWER.get(), 0.7);

        put(DARK_CLAYMORE_ON, () -> ATTACK_DAMAGE, 30.5);
        put(DARK_CLAYMORE_ON, () -> ATTACK_SPEED, -2.7);
        put(DARK_CLAYMORE_ON, () -> ENTITY_REACH.get(), 1.65);
        put(DARK_CLAYMORE_OFF, () -> ATTACK_DAMAGE, 17.0);
        put(DARK_CLAYMORE_OFF, () -> ATTACK_DAMAGE, 17.0);
        put(DARK_CLAYMORE_OFF, () -> ENTITY_REACH.get(), 1.3);
        put(DARK_CLAYMORE_ON_CFG, ()->ATTACK_DAMAGE, IronboundCoreConfig.DARK_CLAYMORE_DAMAGE_ON);
        put(DARK_CLAYMORE_ON_CFG, ()->ATTACK_SPEED, IronboundCoreConfig.DARK_CLAYMORE_ATK_SPEED_ON);
        put(DARK_CLAYMORE_ON_CFG, ()->ENTITY_REACH.get(), IronboundCoreConfig.DARK_CLAYMORE_REACH_ON);
        put(DARK_CLAYMORE_OFF_CFG, ()->ATTACK_DAMAGE, IronboundCoreConfig.DARK_CLAYMORE_DAMAGE_OFF);
        put(DARK_CLAYMORE_OFF_CFG, ()->ATTACK_SPEED, IronboundCoreConfig.DARK_CLAYMORE_ATK_SPEED_OFF);
        put(DARK_CLAYMORE_OFF_CFG, ()->ENTITY_REACH.get(), IronboundCoreConfig.DARK_CLAYMORE_REACH_OFF);

    }

    public static <T> void put(HashMap<Attribute, T> map, Supplier<Attribute> supplier, T value) {
        map.put(supplier.get(), value);
    }
}