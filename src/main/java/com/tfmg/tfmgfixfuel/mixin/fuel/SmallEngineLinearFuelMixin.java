package com.tfmg.tfmgfixfuel.mixin.fuel;

import com.drmangotea.tfmg.content.engines.base.AbstractEngineBlockEntity;
import com.drmangotea.tfmg.content.engines.types.AbstractSmallEngineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSmallEngineBlockEntity.class)
public abstract class SmallEngineLinearFuelMixin extends AbstractEngineBlockEntity {

    protected SmallEngineLinearFuelMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "canWork", at = @At("RETURN"), cancellable = true)
    private void tfmgfixfuel$stallIfNoFuelConsumption(CallbackInfoReturnable<Boolean> cir) {
        if (rpm <= 0.0F) {
            return;
        }
        if (!cir.getReturnValue()) {
            return;
        }
        if (getFuelConsumption() <= 0) {
            cir.setReturnValue(false);
        }
    }
}
