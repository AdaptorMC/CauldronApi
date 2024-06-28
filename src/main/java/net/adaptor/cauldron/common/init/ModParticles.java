package net.adaptor.cauldron.common.init;

import net.adaptor.cauldron.client.particle.CauldronBoilParticle;
import net.adaptor.cauldron.common.CauldronEnhance;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModParticles {
    public static final SimpleParticleType CAULDRONBOIL = FabricParticleTypes.simple(true);

    public static void init() {
        registerParticle(CAULDRONBOIL,"cauldron_boil");
    }
    private static void registerParticle(SimpleParticleType simpleParticleType, String name) {
        Registry.register(Registries.PARTICLE_TYPE, CauldronEnhance.id(name),simpleParticleType);
    }
    public static void registerFactor() {
        ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();
        particleRegistry.register(ModParticles.CAULDRONBOIL, CauldronBoilParticle.Factory::new);
    }
}
