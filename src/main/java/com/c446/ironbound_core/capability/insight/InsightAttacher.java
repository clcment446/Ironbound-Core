package com.c446.ironbound_core.capability.insight;

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

public class InsightAttacher {
    public static class InsightProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        public static final ResourceLocation IDENTIFIER = new ResourceLocation(Ironbound.MOD_ID, "insight");
        public static final Capability<InsightCapability> INSIGHT_CAPABILITY_IDENTIFIER = CapabilityManager.get(new CapabilityToken<InsightCapability>() {
        });
        private final InsightCapability cap = null;
        private final LazyOptional<InsightCapability> optional = LazyOptional.of(this::createInsightCap);

        public static void attach(AttachCapabilitiesEvent<Entity> event) {
            event.addCapability(InsightProvider.IDENTIFIER, new InsightProvider());
        }

        private InsightCapability createInsightCap() {
            if (this.cap == null) {
                return new InsightCapability();
            } else {
                return this.cap;
            }
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == INSIGHT_CAPABILITY_IDENTIFIER) {
                return optional.cast();
            }
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return createInsightCap().save(new CompoundTag());
        }

        @Override
        public void deserializeNBT(CompoundTag compoundTag) {
            createInsightCap().load(compoundTag);
        }
    }
}