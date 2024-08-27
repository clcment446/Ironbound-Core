package com.c446.ironbound_core.registry;

import com.c446.ironbound_core.Ironbound;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IronboundCoreEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Ironbound.MOD_ID);
//    public static final RegistryObject<EntityType<MoonlightRayEntity>> MOONLIGHT_RAY_ENTITY;


//    static {
//        MOONLIGHT_RAY_ENTITY = ENTITIES.register("moonlight_ray", () -> {
//            return EntityType.Builder.of(MoonlightRayEntity::new, MobCategory.MISC)
//                    .sized(2.0F, 0.5F)
//                    .clientTrackingRange(64)
//                    .build((new ResourceLocation("irons_spellbooks", "moonlight_ray")).toString());});
//    }
//}
}
