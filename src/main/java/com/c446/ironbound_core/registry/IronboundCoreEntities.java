package com.c446.ironbound_core.registry;

import com.c446.ironbound_core.IronBound;
import com.c446.ironbound_core.entity.spells.MoonlightRayEntity;
import io.redspace.ironsspellbooks.entity.spells.blood_slash.BloodSlashProjectile;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IronboundCoreEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, IronBound.MOD_ID);
//    public static final RegistryObject<EntityType<Entity>> MOONLIGHT_RAY_ENTITY;
//
//    private static final RegistryObject<EntityType<Entity>> BLOOD_SLASH_PROJECTILE;
//
//    static {
//        ENTITIES = ;
//
//        MOONLIGHT_RAY_ENTITY = ENTITIES.register("moonlight_ray", () -> {
//            return EntityType.Builder.of(MoonlightRayEntity::new, MobCategory.MISC)
//                    .sized(2.0F, 0.5F)
//                    .clientTrackingRange(64)
//                    .build(
//                            new ResourceLocation("irons_spellbooks", "moonlight_ray").toString());
//        });
//
//        BLOOD_SLASH_PROJECTILE = ENTITIES.register("blood_slash", () -> {
//            return EntityType.Builder.of(BloodSlashProjectile::new, MobCategory.MISC).sized(2.0F, 0.5F).clientTrackingRange(64).build((new ResourceLocation("irons_spellbooks", "blood_slash")).toString());
//        });
//    }

    public static <T extends Entity> EntityType.Builder<T> createBuilder(EntityType.EntityFactory<T> factory, MobCategory category) {
        return EntityType.Builder.of(factory, category);
    }

    public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) {
        return ENTITIES.register(name, () -> builder.build(new ResourceLocation(IronBound.MOD_ID, name).toString()));
    }
}
