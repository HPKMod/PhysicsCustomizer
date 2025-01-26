package io.github.hyper1423.physicscustomizer.mixin.client;

import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfigKeys;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PaneBlock.class)
public abstract class PaneBlockMixin extends HorizontalConnectingBlock {
    @Shadow public abstract boolean connectsTo(BlockState state, boolean sideSolidFullSquare);

    @Unique private VoxelShape[] legacyCollisionShapes;

    protected PaneBlockMixin(float radius1, float radius2, float boundingHeight1, float boundingHeight2, float collisionHeight, Settings settings) {
        super(radius1, radius2, boundingHeight1, boundingHeight2, collisionHeight, settings);
    }

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void constructShapes(Settings settings, CallbackInfo ci) {
        float radius2 = 1.0F;
        float offset2 = 0.0F;
        float height2 = 16.0F;

        float low = 8.0F - radius2;
        float high = 8.0F + radius2;
        float mid = 8.0F;

        VoxelShape negativeZ = Block.createCuboidShape(
                low,    0,      0,
                high,   16,     mid
        );
        VoxelShape positiveZ = Block.createCuboidShape(
                low,    0,      mid,
                high,   16,     16
        );
        VoxelShape negativeX = Block.createCuboidShape(
                0,      0,      low,
                mid,    16,     high
        );
        VoxelShape positiveX = Block.createCuboidShape(
                mid,    0,      low,
                16,     16,     high
        );
        VoxelShape posXNegZ = VoxelShapes.union(negativeZ, positiveX);
        VoxelShape negXPosZ = VoxelShapes.union(positiveZ, negativeX);

        // +X: 1000
        // -Z:  100
        // -X:   10
        // +Z:    1
        this.legacyCollisionShapes = new VoxelShape[]{
                VoxelShapes.union(negXPosZ, posXNegZ),
                positiveZ,
                negativeX,
                negXPosZ,
                negativeZ,
                VoxelShapes.union(positiveZ, negativeZ),
                VoxelShapes.union(negativeX, negativeZ),
                VoxelShapes.union(negXPosZ, negativeZ),
                positiveX,
                VoxelShapes.union(positiveZ, positiveX),
                VoxelShapes.union(negativeX, positiveX),
                VoxelShapes.union(negXPosZ, positiveX),
                posXNegZ,
                VoxelShapes.union(positiveZ, posXNegZ),
                VoxelShapes.union(negativeX, posXNegZ),
                VoxelShapes.union(negXPosZ, posXNegZ)
        };
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.USE_OLD_PANE_HITBOX).getValue()
                ? this.legacyCollisionShapes[this.getShapeIndex(state)]
                : this.collisionShapes[this.getShapeIndex(state)];
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int stateNo = this.getShapeIndex(state);
        return PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.USE_OLD_PANE_HITBOX).getValue() && stateNo == 0
                ? this.boundingShapes[15]
                : this.boundingShapes[stateNo];
    }
}
