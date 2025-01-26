package io.github.hyper1423.physicscustomizer.mixin.client;

import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfigKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SoulSandBlock.class)
public class SoulSandBlockMixin extends Block {
    public SoulSandBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public float getVelocityMultiplier() {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.SOUL_SAND_USE_COLLISION_INSTEAD).getValue()) {
            return 1.0f;
        } else {
            return this.velocityMultiplier;
        }
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.SOUL_SAND_USE_COLLISION_INSTEAD).getValue()) {
            var vel = entity.getVelocity();
            entity.setVelocity(vel.getX() * this.velocityMultiplier, vel.getY(), vel.getZ() * this.velocityMultiplier);
        } else {
            super.onEntityCollision(state, world, pos, entity);
        }
    }
}
