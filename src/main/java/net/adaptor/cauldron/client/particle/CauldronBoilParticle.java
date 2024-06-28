package net.adaptor.cauldron.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class CauldronBoilParticle extends SpriteBillboardParticle {
    protected final SpriteProvider spriteProvider;

    protected CauldronBoilParticle(ClientWorld clientWorld, double d, double e, double f, SpriteProvider spriteProvider, double g, double h, double i) {
        super(clientWorld, d, e, f);
        this.spriteProvider = spriteProvider;
        this.gravityStrength = -0.125f;
        this.velocityMultiplier = 0.85f;
        this.setBoundingBoxSpacing(0.02f, 0.02f);
        this.scale *= this.random.nextFloat() * 0.6f + 0.2f;
        this.velocityX = g * (double) 0.2f + (Math.random() * 2.0 - 1.0) * (double) 0.02f;
        this.velocityY = h * (double) 0.2f + (Math.random() * 2.0 - 1.0) * (double) 0.02f;
        this.velocityZ = i * (double) 0.2f + (Math.random() * 2.0 - 1.0) * (double) 0.02f;
        this.maxAge = (int) (40.0 / (Math.random() * 0.8 + 0.2));
        this.setSpriteForAge(this.spriteProvider);
    }


    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.getBlockState(BlockPos.ofFloored(this.x, this.y, this.z)).isIn(BlockTags.CAULDRONS)) {
            this.world.addParticle(ParticleTypes.BUBBLE_POP, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
            this.world.playSound(this.x,this.y,this.z,SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP,SoundCategory.BLOCKS,1.0f, (float) (1.1f + this.world.random.nextGaussian() / 20f),true);
            this.markDead();
        }
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider sprites) implements ParticleFactory<SimpleParticleType> {
        @Override
        public @NotNull Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new CauldronBoilParticle(world,x,y,z,sprites,velocityX,velocityY,velocityZ);
        }
    }
}
