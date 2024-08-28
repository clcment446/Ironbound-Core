package com.c446.ironbound_core.items;

import com.c446.ironbound_core.registry.IronboundCoreEntities;
import com.c446.ironbound_core.registry.IronboundCoreItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static com.c446.ironbound_core.Ironbound.MOD_ID;
import static com.c446.ironbound_core.registry.IronboundCoreItems.HOLY_SWORD;

public class RegularItems extends Item {
    boolean showEnch = false;
    String name;

    public RegularItems(Properties p) {
        super(p);
    }

    static {
        ItemProperties.register(HOLY_SWORD.get(), new ResourceLocation(MOD_ID, "holy_sword_trick_weapon_status"), (stack, clientLevel, livingEntity, integer) -> {
            if (livingEntity != null && stack.getItem() instanceof GenericTrickWeapon && Objects.equals(GenericTrickWeapon.getState(stack), true)) {
                // CHECK THAT LIVING NOT NULL AND STACK IS TRICK WEAPON
                return 1.0F;
            } else if ((livingEntity != null && stack.getItem() instanceof GenericTrickWeapon && Objects.equals(GenericTrickWeapon.getState(stack), false))) {
                return 0.0F;
            } else {
                return -1.0F;
            }
        });
    }

    public RegularItems(Properties p, boolean showEnch) {
        super(p);
        this.showEnch = showEnch;
    }

    @Override
    public boolean isFoil(ItemStack s) {
        if (showEnch) {
            return true;
        } else {
            return super.isFoil(s);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

    }
}
