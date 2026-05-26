package com.tfmg.tfmgfixfuel.mixin;

import com.drmangotea.tfmg.content.engines.types.AbstractSmallEngineBlockEntity;
import com.drmangotea.tfmg.registry.TFMGDataComponents;
import com.drmangotea.tfmg.registry.TFMGItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSmallEngineBlockEntity.class)
public abstract class EmptyOilCanCrashFixMixin {

    @Inject(method = "insertItem", at = @At("HEAD"), cancellable = true)
    private void tfmgfixfuel$preventCrashOnEmptyCan(
            ItemStack itemStack, boolean shifting, Player player, InteractionHand hand,
            CallbackInfoReturnable<Boolean> cir) {
        if (itemStack.is(TFMGItems.OIL_CAN.get()) || itemStack.is(TFMGItems.COOLING_FLUID_BOTTLE.get())) {
            if (itemStack.get(TFMGDataComponents.AMOUNT) == null) {
                cir.setReturnValue(false);
            }
        }
    }
}
