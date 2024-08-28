package com.c446.ironbound_core.registry;

import com.c446.ironbound_core.Ironbound;
import com.c446.ironbound_core.entity.spells.MoonlightRayEntity;
import io.redspace.ironsspellbooks.entity.spells.blood_slash.BloodSlashProjectile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IronboundCoreEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Ironbound.MOD_ID);
    
    public static final RegistryObject<EntityType<MoonlightRayEntity>> MOONLIGHT_RAY_ENTITY;
    
    static {
        MOONLIGHT_RAY_ENTITY = registerEntity("moonlight_ray", EntityType.Builder.<MoonlightRayEntity>of(MoonlightRayEntity::new, MobCategory.MISC).sized(2.0F, 0.5F).clientTrackingRange(64));
    }
    
	public static <T extends Entity> EntityType.Builder<T> createBuilder(EntityType.EntityFactory<T> factory, MobCategory category) {
		return EntityType.Builder.<T>of(factory, category);
	}
	
	public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) {
		return ENTITIES.register(name, () -> builder.build(new ResourceLocation(Ironbound.MOD_ID, name).toString()));
	}
}
