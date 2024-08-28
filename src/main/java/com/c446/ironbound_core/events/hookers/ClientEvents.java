package com.c446.ironbound_core.events.hookers;

import com.c446.ironbound_core.Ironbound;
import com.c446.ironbound_core.items.GenericTrickWeapon;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Objects;

import static com.c446.ironbound_core.Ironbound.MOD_ID;
import static com.c446.ironbound_core.registry.IronboundCoreItems.HOLY_SWORD;

@Mod.EventBusSubscriber(modid = Ironbound.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

	@SubscribeEvent
	public static void onFMLClientSetup(FMLClientSetupEvent event) {
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
}
