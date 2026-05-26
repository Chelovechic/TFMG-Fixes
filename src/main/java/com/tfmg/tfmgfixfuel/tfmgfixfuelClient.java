package com.tfmg.tfmgfixfuel;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = tfmgfixfuel.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = tfmgfixfuel.MODID, value = Dist.CLIENT)
public class tfmgfixfuelClient {
    public tfmgfixfuelClient(ModContainer container) {
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
    }
}
