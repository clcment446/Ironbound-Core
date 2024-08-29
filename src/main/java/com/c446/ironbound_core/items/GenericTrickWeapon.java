package com.c446.ironbound_core.items;

import com.c446.ironbound_core.util.ItemAttributeHelper;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class GenericTrickWeapon extends Item {
    private final ResourceLocation[] textures;
    private final HashMap<Attribute, ForgeConfigSpec.DoubleValue> trickStateOnConfigMap;
    private final HashMap<Attribute, Double> trickStateOnDefaultMap;
    private final HashMap<Attribute, ForgeConfigSpec.DoubleValue> trickStateOffConfigMap;
    private final HashMap<Attribute, Double> trickStateOffDefaultMap;
    private final UUID attributeUUIDs;

    public GenericTrickWeapon(Properties pProperties, @NotNull ResourceLocation[] textures, HashMap<Attribute, ForgeConfigSpec.DoubleValue> trickStateOnConfigMap, HashMap<Attribute, Double> trickStateOnDefaultMap, HashMap<Attribute, ForgeConfigSpec.DoubleValue> trickStateOffConfigMap, HashMap<Attribute, Double> trickStateOffDefaultMap, UUID attributeUUIDs) {
        super(pProperties);
        this.textures = textures;
        this.trickStateOnConfigMap = trickStateOnConfigMap;
        this.trickStateOnDefaultMap = trickStateOnDefaultMap;
        this.trickStateOffConfigMap = trickStateOffConfigMap;
        this.trickStateOffDefaultMap = trickStateOffDefaultMap;
        this.attributeUUIDs = attributeUUIDs;
    }

    public static void changeState(boolean newState, ItemStack stack) {
        if (!(stack.getItem() instanceof GenericTrickWeapon)) {
            return;
        }

        if (stack.getTag() != null && stack.hasTag() && stack.getTag().contains("trick_weapon_activated")) {
            stack.getTag().putBoolean("trick_weapon_activated", newState);
            System.out.println("Setting trick state to: " + (newState ? "activated" : "deactivated"));
        }
    }

    public static void invertState(ItemStack stack) {
        changeState(!getState(stack), stack);
    }

    public static boolean getState(ItemStack stack) {
        if (stack.getItem() instanceof GenericTrickWeapon) {
            System.out.println("TRICK WEAPON LOCATED !!!");

            CompoundTag tag = stack.getTag();
            if (tag == null) {
                tag = new CompoundTag();
                stack.setTag(tag);
            }

            if (!tag.contains("trick_weapon_activated")) {
                tag.putBoolean("trick_weapon_activated", false);
            }

            boolean activated = tag.getBoolean("trick_weapon_activated");
            System.out.println("Trick weapon's state is " + (activated ? "activated" : "deactivated"));
            return activated;
        }

        System.out.println("Tried to access generic trick weapon method from non-generic trick weapon item... Wtf?");
        return false;
    }


    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        invertState(pContext.getItemInHand());

        // DO ANIM THINGS

        return super.useOn(pContext);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {

        Multimap<Attribute, AttributeModifier> values = super.getAttributeModifiers(slot, stack);

        if (Objects.equals(GenericTrickWeapon.getState(stack), true)) {
            ItemAttributeHelper.handleConfig(this.trickStateOnConfigMap, this.trickStateOnDefaultMap);
        } else if (Objects.equals(GenericTrickWeapon.getState(stack), false)) {
            ItemAttributeHelper.handleConfig(this.trickStateOffConfigMap, this.trickStateOffDefaultMap);
        }
        return values;
    }
}
