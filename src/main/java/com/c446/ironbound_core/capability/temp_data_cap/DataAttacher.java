package com.c446.ironbound_core.capability.temp_data_cap;

import com.c446.ironbound_core.Ironbound;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataAttacher {
    public static class DataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        public static final ResourceLocation IDENTIFIER = new ResourceLocation(Ironbound.MOD_ID, "ironbound_heart_data_cap");
        public static final Capability<DataCap> DATA_CAP_CAPABILITY = CapabilityManager.get(new CapabilityToken<DataCap>() {
        });
        private DataCap cap = null;
        private final LazyOptional<DataCap> optional = LazyOptional.of(this::createStatusResistanceCap);

        private DataCap createStatusResistanceCap() {
            if (this.cap == null) {
                return new DataCap();
            } else {
                return this.cap;
            }
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == DATA_CAP_CAPABILITY) {
                return optional.cast();
            }
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return new CompoundTag();
        }

        @Override
        public void deserializeNBT(CompoundTag compoundTag) {

        }

        public static void attach(AttachCapabilitiesEvent<Entity> event){
            event.addCapability(DataProvider.IDENTIFIER, new DataProvider());
        }
    }
}
