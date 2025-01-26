package io.github.hyper1423.physicscustomizer.mixin.client;

import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.config.BlipFallbackMode;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfigKeys;
import io.github.hyper1423.physicscustomizer.config.WallCollisionRule;
import it.unimi.dsi.fastutil.floats.FloatSet;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract Box getBoundingBox();

    /* ----- WALL_COLLISION_RULE ----- */

    @ModifyVariable(
            method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/List;)Lnet/minecraft/util/math/Vec3d;",
            at = @At("STORE"),
            index = 9 // boolean bl
    )
    private static boolean onFacingCheck(boolean speedZGreaterThanX) {
        var facingRule = PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.WALL_COLLISION_RULE).getValue();
        return switch (facingRule) {
            case FASTER_THEN_SLOWER -> speedZGreaterThanX;
            case SLOWER_THEN_FASTER -> !speedZGreaterThanX;
            case X_THEN_Z -> false;
            case Z_THEN_X -> true;
            case X_AND_Z, X_OR_Z -> false; // Let's check for X first. We're going to check for Z below.
        };
    }

    // Well, in principle, this should NOT throw NPE... at least until Mojang stops torturing core #0
    @Unique private static Box originalEBB = null;

    @Inject(
            method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/List;)Lnet/minecraft/util/math/Vec3d;",
            at = @At(value = "HEAD")
    )
    private static void saveEntityBoundingBox(Vec3d movement, Box entityBoundingBox, List<VoxelShape> collisions, CallbackInfoReturnable<Vec3d> cir) {
        originalEBB = entityBoundingBox;
    }

    @Inject(
            method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/List;)Lnet/minecraft/util/math/Vec3d;",
            at = @At(value = "TAIL"),
            cancellable = true
    )
    private static void overwriteCollisionCheck(Vec3d movement, Box entityBoundingBox, List<VoxelShape> collisions, CallbackInfoReturnable<Vec3d> cir) {
        var facingRule = PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.WALL_COLLISION_RULE).getValue();
        if (facingRule != WallCollisionRule.X_AND_Z && facingRule != WallCollisionRule.X_OR_Z) {
            return;
        }

        // The following code is from Entity#adjustMovementForCollisions(Vec3d, Box, List<VoxelShape>)
        double x2 = movement.x;
        double z2 = movement.z;

        double y = movement.y;
        if (y != 0.0) {
            y = VoxelShapes.calculateMaxOffset(Direction.Axis.Y, entityBoundingBox, collisions, y);
            if (y != 0.0) {
                originalEBB = originalEBB.offset(0.0, y, 0.0);
            }
        }

        boolean bl = true;
        if (bl && z2 != 0.0) {
            z2 = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, originalEBB, collisions, z2);
            if (z2 != 0.0) {
                originalEBB = originalEBB.offset(0.0, 0.0, z2);
            }
        }

        if (x2 != 0.0) {
            x2 = VoxelShapes.calculateMaxOffset(Direction.Axis.X, originalEBB, collisions, x2);
            if (!bl && x2 != 0.0) {
                originalEBB = originalEBB.offset(x2, 0.0, 0.0);
            }
        }

        if (!bl && z2 != 0.0) {
            z2 = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, originalEBB, collisions, z2);
        }
        // Code end

        Vec3d option1 = cir.getReturnValue();

        if (facingRule == WallCollisionRule.X_AND_Z) {
            double smallerX, smallerZ;
            if (Math.abs(x2) < Math.abs(option1.x)) { smallerX = x2; }
            else { smallerX = option1.x; }
            if (Math.abs(z2) < Math.abs(option1.z)) { smallerZ = z2; }
            else { smallerZ = option1.z; }

            cir.setReturnValue(new Vec3d(smallerX, option1.y, smallerZ));
        } else {
            double biggerX, biggerZ;
            if (Math.abs(x2) > Math.abs(option1.x)) { biggerX = x2; }
            else { biggerX = option1.x; }
            if (Math.abs(z2) > Math.abs(option1.z)) { biggerZ = z2; }
            else { biggerZ = option1.z; }

            cir.setReturnValue(new Vec3d(biggerX, option1.y, biggerZ));
        }
    }

    /* ----- ALLOW_BLIP ----- */

    @ModifyVariable(
            method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;",
            at = @At("STORE"),
            index = 9 // Box box2
    )
    private Box onSteppingFallingCheck(Box box2) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.ALLOW_BLIP).getValue()) {
            return this.getBoundingBox();
        }
        return box2;
    }

    /* ----- BLIP_FALLBACK_MODE ----- */

    @ModifyVariable(
            method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;",
            at = @At("STORE"),
            index = 12 // float f
    )
    private float useMovementYInstead(float f, Vec3d movement) {
        var fallbackMode = PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.BLIP_FALLBACK_MODE).getValue();
        return switch (fallbackMode) {
            case BLIP_DOWN -> (float) movement.y;
            case BLIP_UP -> 0.0F;
            default -> f;
        };
    }

    @ModifyVariable(
            method = "collectStepHeights",
            at = @At("STORE"),
            index = 4 // FloatSet floatSet
    )
    private static FloatSet addFallbackStepHeight(FloatSet instance, Box collisionBox, List<VoxelShape> collisions, float f, float stepHeight) {
        var fallbackMode = PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.BLIP_FALLBACK_MODE).getValue();
        if (fallbackMode != BlipFallbackMode.NONE) {
            instance.add(stepHeight); // The names of 'f' and 'stepHeight' are swapped... for some reason...
        }
        return instance;
    }

    /* ----- EXPAND_SUPPORTING_BLOCK_HITBOX ----- */

    @Redirect(
            method = "updateSupportingBlockPos",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;findSupportingBlockPos(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/Optional;"
            )
    )
    private Optional<BlockPos> useLegacySupportingBlockLogic(World instance, Entity entity, Box box) {
        if (!PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.EXPAND_SUPPORTING_BLOCK_HITBOX).getValue()) {
            // This code snippet is from 1.8.9
            int x = MathHelper.floor(entity.getPos().x);
            int y = MathHelper.floor(entity.getPos().y - 0.20000000298023224D);
            int z = MathHelper.floor(entity.getPos().z);
            BlockPos bPos = new BlockPos(x, y, z);
            BlockState bState = instance.getBlockState(bPos);

            if (bState.isAir()) {
                BlockState downBState = instance.getBlockState(bPos.down());

                if (!downBState.isAir() && downBState.getCollisionShape(instance, bPos).getMax(Direction.Axis.Y) > 1.0) {
                    bState = downBState;
                    bPos = bPos.down();
                }
            }
            // ...until here

            return Optional.of(bPos);
        }
        return instance.findSupportingBlockPos(entity, box);
    }
    @ModifyConstant(
            method = "checkBlockCollision",
            constant = @Constant(doubleValue = (double) 1E-5F)
    )
    private double modifyHitboxContraction(double old) {
        return PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.COLLISION_HITBOX_CONTRACTION).getValue();
    }

    /* ----- PRE_1_13_SWIMMING ----- */

    @Shadow public abstract void setSwimming(boolean swimming);

    @Redirect(
            method = "updateMovementInFluid",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/Box;contract(D)Lnet/minecraft/util/math/Box;"
            )
    )
    private Box contractMore(Box instance, double value) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
            return instance.contract(0, 0.4f, 0).contract(value);
        }
        return instance.contract(value);
    }

    @Redirect(
            method = "updateMovementInFluid",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/util/math/Box;minY:D",
                    opcode = Opcodes.GETFIELD
//                    ordinal = 1
            )
    )
    private double useFlooredMinYInstead(Box box) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
            return MathHelper.floor(box.minY);
        }
        return box.minY;
    }

    @ModifyConstant(
            method = "updateMovementInFluid",
            constant = @Constant(doubleValue = 0.4)
    )
    private double doNotReduceFluidVelocity(double original) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
            return -1.0; // Effectively disables the condition check
        }
        return original;
    }

    @Inject(
            method = "getSwimHeight",
            at = @At("HEAD"),
            cancellable = true
    )
    private void disableSwimHeight(CallbackInfoReturnable<Double> cir) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
            cir.setReturnValue(0.0);
        }
    }

    @Inject(
            method = "updateSwimming",
            at = @At("HEAD"),
            cancellable = true
    )
    private void disableSwimming(CallbackInfo ci) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
            this.setSwimming(false);
            ci.cancel();
        }
    }
}
