package com.tfmg.tfmgfixfuel.mixin;

import com.drmangotea.tfmg.content.engines.types.regular_engine.RegularEngineBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RegularEngineBlockEntity.class)
public interface RegularEngineBlockEntityAccessor {
    @Accessor("pistonInventory")
    SmartInventory tfmgfixfuel$getPistonInventory();
}
