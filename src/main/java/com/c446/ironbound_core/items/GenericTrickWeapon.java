package com.c446.ironbound_core.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class GenericTrickWeapon extends GenericAttributeItem {
    private final ResourceLocation[] textures;

    public GenericTrickWeapon(Properties pProperties, @NotNull ResourceLocation[] textures, HashMap<Attribute, ForgeConfigSpec.DoubleValue> attributeMap, HashMap<Attribute, Double> defaultMap, UUID attributeUUIDs) {
        super(pProperties, attributeMap, defaultMap, attributeUUIDs);
        this.textures = textures;
    }

    public static void changeState(boolean state, ItemStack stack) {
        if (!(stack.getItem() instanceof GenericTrickWeapon)) {
            return;
        }
        if (stack.hasTag() && stack.getTag() != null && stack.getTag().contains("trick_weapon_activated")) {
            stack.getTag().putBoolean("trick_weapon_activated", stack.getTag().getBoolean("trick_weapon_activated"));
        }
    }

    public static void invertState(ItemStack stack) {
        changeState(Objects.requireNonNull(getState(stack)).equals(true), stack);
    }

    public static Object getState(ItemStack stack) {
        if ((stack.getItem() instanceof GenericTrickWeapon trickWeapon)) {
            if (stack.getTag() == null) {
                stack.setTag(new CompoundTag());
                stack.getTag().putBoolean("trick_weapon_activated", false);
            }
            else if (!(stack.getTag().contains("trick_weapon_activated"))){
                stack.getTag().putBoolean("trick_weapon_activated", false);
            }
            return stack.getTag().getBoolean("trick_weapon_activated");
        }
        return null;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        invertState(pContext.getItemInHand());

        // DO ANIM THINGS

        return super.useOn(pContext);
    }
}
