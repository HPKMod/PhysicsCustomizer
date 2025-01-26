package io.github.hyper1423.physicscustomizer.mixin.client;

import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfigKeys;
import net.minecraft.block.*;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LadderBlock.class)
public class LadderBlockMixin {
    @Shadow public static @Final EnumProperty<Direction> FACING;
    @Unique private static final VoxelShape THIN_EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 16.0);
    @Unique private static final VoxelShape THIN_WEST_SHAPE = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    @Unique private static final VoxelShape THIN_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 2.0);
    @Unique private static final VoxelShape THIN_NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 16.0, 16.0);

    @Inject(
            method = "getOutlineShape",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (!PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.THINNER_LADDER).getValue()) {
            return;
        }
        cir.setReturnValue(switch (state.get(FACING)) {
            case NORTH -> THIN_NORTH_SHAPE;
            case SOUTH -> THIN_SOUTH_SHAPE;
            case WEST -> THIN_WEST_SHAPE;
            case EAST -> THIN_EAST_SHAPE;
            default -> throw new IllegalStateException("Invalid direction of ladder: %s".formatted(FACING));
        });
    }
}
