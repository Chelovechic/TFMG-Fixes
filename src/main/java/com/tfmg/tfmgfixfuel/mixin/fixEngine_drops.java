package com.tfmg.tfmgfixfuel.mixin;

import com.drmangotea.tfmg.content.engines.base.EngineBlock;
import com.drmangotea.tfmg.content.engines.base.EngineComponentsInventory;
import com.drmangotea.tfmg.content.engines.types.AbstractSmallEngineBlockEntity;
import com.drmangotea.tfmg.content.engines.types.radial_engine.RadialEngineBlockEntity;
import com.drmangotea.tfmg.content.engines.types.turbine_engine.TurbineEngineBlockEntity;
import com.drmangotea.tfmg.content.engines.upgrades.EngineUpgrade;
import com.simibubi.create.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AbstractSmallEngineBlockEntity.class)
public abstract class fixEngine_drops extends com.drmangotea.tfmg.content.engines.base.AbstractEngineBlockEntity {

    @Shadow
    public Optional<? extends EngineUpgrade> upgrade;
    @Shadow
    public EngineComponentsInventory componentsInventory;

    @Unique
    private boolean tfmgfixfuel$droppedContents;

    protected fixEngine_drops(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "remove", at = @At("HEAD"))
    private void tfmgfixfuel$dropStoredParts(CallbackInfo ci) {
        if (level == null || level.isClientSide || tfmgfixfuel$droppedContents) {
            return;
        }

        tfmgfixfuel$droppedContents = true;

        if (upgrade.isPresent()) {
            dropItem(upgrade.get().getItem().getDefaultInstance());
            upgrade = Optional.empty();
        }

        if (!(((Object) this) instanceof RadialEngineBlockEntity) && !(((Object) this) instanceof TurbineEngineBlockEntity)
                && getBlockState().hasProperty(EngineBlock.ENGINE_STATE)
                && getBlockState().getValue(EngineBlock.ENGINE_STATE) == EngineBlock.EngineState.SHAFT) {
            dropItem(AllBlocks.SHAFT.asStack());
        }

        for (int slot = 0; slot < componentsInventory.getSlots(); slot++) {
            ItemStack stack = componentsInventory.getStackInSlot(slot);
            if (stack.isEmpty()) {
                continue;
            }

            dropItem(stack.copy());
            componentsInventory.setStackInSlot(slot, ItemStack.EMPTY);
        }

        if ((Object) this instanceof RegularEngineBlockEntityAccessor regularAccessor) {
            for (int slot = 0; slot < regularAccessor.tfmgfixfuel$getPistonInventory().getSlots(); slot++) {
                ItemStack stack = regularAccessor.tfmgfixfuel$getPistonInventory().getItem(slot);
                if (stack.isEmpty()) {
                    continue;
                }

                dropItem(stack.copy());
                regularAccessor.tfmgfixfuel$getPistonInventory().setItem(slot, ItemStack.EMPTY);
            }
        }
    }
}
