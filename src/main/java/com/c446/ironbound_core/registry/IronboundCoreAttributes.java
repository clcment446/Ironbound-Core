package com.c446.ironbound_core.registry;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

import com.c446.ironbound_core.Ironbound;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IronboundCoreAttributes {

    public static final HashMap<RegistryObject<Attribute>, UUID> UUIDS = new HashMap<>();
    public static final DeferredRegister<Attribute> ATTRIBUTES;

    public static final RegistryObject<Attribute> VITALITY_ATTRIBUTE;
    public static final RegistryObject<Attribute> FOCUS_ATTRIBUTE;
    public static RegistryObject<Attribute> UNDEAD_DAMAGE;

    public static final RegistryObject<Attribute> INSIGHT_ATTRIBUTE;
    public static RegistryObject<Attribute> LAPIS_FORTUNE;
    public static RegistryObject<Attribute> DIAMOND_FORTUNE;
    public static RegistryObject<Attribute> COAL_FORTUNE;
    public static RegistryObject<Attribute> REDSTONE_FORTUNE;
    public static RegistryObject<Attribute> IRON_FORTUNE;
    public static RegistryObject<Attribute> GOLD_FORTUNE;

    static {
        ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Ironbound.MOD_ID);

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
    
    /**
     * @Param caster : the caster
     * @Param target : the target
     * @Param coefficent : how steep the insight difference will be. Use something around 0.3 for best results.
     * @f
     */
    public static double calcCounterSpellEvasionChance(Player caster, Player target, float coefficient) {
        float insightDiff = (float) (target.getAttributeValue(INSIGHT_ATTRIBUTE.get()) - caster.getAttributeValue(INSIGHT_ATTRIBUTE.get()));
        return Math.max(1, Math.exp(coefficient * insightDiff));
    }

    public static double getAttributeValue(LivingEntity entity, Attribute attribute) {
        AttributeInstance inst = entity.getAttribute(attribute);
        if (inst == null) return -Integer.MAX_VALUE;
        else return inst.getValue();
    }
}
