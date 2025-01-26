package io.github.hyper1423.physicscustomizer.mixin.client;

import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfigKeys;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    /* ----- INERTIA_THRESHOLD ----- */

    @ModifyConstant(method = "tickMovement", constant = @Constant(doubleValue = 0.003))
    private double replaceInertiaThreshold(double old) {
        return PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.INERTIA_THRESHOLD).getValue();
    }

    /* ----- DELAY_MIDAIR_SPRINT ----- */

    @Shadow protected abstract float getOffGroundSpeed();

    @Unique protected float oldOffGroundSpeed = 0.02f;

    @Redirect(
            method = "getMovementSpeed(F)F",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getOffGroundSpeed()F"
            )
    )
    private float useCachedOffGroundSpeed(LivingEntity instance) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.DELAY_MIDAIR_SPRINT).getValue()) {
            return oldOffGroundSpeed;
        }
        return this.getOffGroundSpeed();
    }

    @Inject(
            method = "applyMovementInput",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getVelocity()Lnet/minecraft/util/math/Vec3d;",
                    ordinal = 2
            )
    )
    private void lateUpdateOffGroundSpeed(Vec3d movementInput, float slipperiness, CallbackInfoReturnable<Vec3d> cir) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.DELAY_MIDAIR_SPRINT).getValue()) {
            oldOffGroundSpeed = this.getOffGroundSpeed();
        }
    }

    /* ----- ALLOW_CLIMBING_BY_JUMP ----- */

    @Shadow protected boolean jumping;

    @Redirect(
            method = "applyMovementInput",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/LivingEntity;jumping:Z",
                    opcode = Opcodes.GETFIELD
            )
    )
    private boolean disableJumpClimbing(LivingEntity instance) {
        assert this == (Object) instance; // Yup this method never accesses LivingEntity#jumping of other objects
        if (!PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.ALLOW_CLIMBING_BY_JUMP).getValue()) {
            return false;
        }
        return this.jumping;
    }

    /* ----- NO_DRAG ----- */

    @Inject(
            method = "hasNoDrag",
            at = @At("HEAD"),
            cancellable = true
    )
    private void funny(CallbackInfoReturnable<Boolean> cir) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.NO_DRAG).getValue()) {
            cir.setReturnValue(true);
        }
    }

    /* ----- PRE_1_13_SWIMMING ----- */

    @Inject(
            method = "applyFluidMovingSpeed",
            at = @At(
                    value = "RETURN",
                    ordinal = 0
            ),
            cancellable = true
    )
    private void makeFluidGravityStronger(double gravity, boolean falling, Vec3d motion, CallbackInfoReturnable<Vec3d> cir) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
            cir.setReturnValue(new Vec3d(motion.x, motion.y - 0.02d, motion.z));
        }
    }

    @Redirect(
            method = "travelInFluid",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;isSprinting()Z"
            )
    )
    private boolean disableSprintingSpeedIncrease(LivingEntity instance) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
            return false;
        }
        return instance.isSprinting();
    }

    @Redirect(
            method = "applyFluidMovingSpeed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;isSprinting()Z"
            )
    )
    private boolean disableSprintingGravityDecrease(LivingEntity instance) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
            return false;
        }
        return instance.isSprinting();
    }
}
