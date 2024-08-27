package com.c446.ironbound_core;

import com.c446.ironbound_core.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Ironbound.MOD_ID)
public class Ironbound {
    public static final String MOD_ID = "ironbound_core";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Ironbound() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, com.c446.ironbound_core.config.IronboundCoreConfig.SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, com.c446.ironbound_core.config.IronboundCoreConfig.COMMON_CONFIG);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
        IronboundCoreBlocks.BLOCKS.register(bus);
        IronboundCoreItems.ITEMS.register(bus);
        IronboundCoreEntities.ENTITIES.register(bus);
        IronboundCorePotions.EFFECTS.register(bus);
        IronboundCoreSpells.SPELLS.register(bus);
        IronboundCoreAttributes.ATTRIBUTES.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }
}
