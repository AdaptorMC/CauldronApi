package net.adaptor.cauldron.client;

import net.adaptor.cauldron.common.init.ModParticles;
import net.fabricmc.api.ClientModInitializer;

public class CauldronEnchanceClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModParticles.registerFactor();
    }
}
