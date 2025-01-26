package io.github.hyper1423.physicscustomizer.mixin.client;

import com.mojang.authlib.GameProfile;
import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfigKeys;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Shadow public Input input;
    @Shadow public abstract boolean isSubmergedInWater();
    @Shadow public abstract boolean shouldSlowDown();
    @Shadow public abstract boolean isUsingItem();

    @Inject(method = "isWalking()Z", at = @At("HEAD"), cancellable = true)
    private void onIsWalking(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(
            this.isSubmergedInWater() ? this.input.hasForwardMovement() : (double)this.input.movementForward >= 0.7
        );
    }

    @Redirect(
            method = "shouldStopSprinting()Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;shouldSlowDown()Z"
            )
    )
    private boolean redirectShouldSlowDown(ClientPlayerEntity instance) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.ALLOW_SNEAK_SPRINT).getValue()) {
            return false;
        } else {
            return this.shouldSlowDown();
        }
    }

    @Redirect(
            method = "shouldStopSprinting()Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"
            )
    )
    private boolean redirectIsUsingItem(ClientPlayerEntity instance) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.ALLOW_SNEAK_SPRINT).getValue()) {
            return false;
        } else {
            return this.isUsingItem();
        }
    }

    /* ----- PRE_1_13_SWIMMING ----- */

    @Shadow protected abstract boolean canSprint();

    @ModifyVariable(
            method = "tickMovement",
            at = @At("STORE"),
            name = "bl9"
    )
    private boolean allowSprintingOverWater(boolean original) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
            return !this.input.hasForwardMovement() || !this.canSprint() || this.horizontalCollision && !this.collidedSoftly;
        }
        return original;
    }

    @Override
    protected void knockDownwards() {
        if (!PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
            super.knockDownwards();
        }
    }

    /* ----- DISABLE_SOFT_COLLISION ----- */

    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;collidedSoftly:Z"
            )
    )
    protected boolean redirectCollidedSoftly(ClientPlayerEntity instance) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.DISABLE_SOFT_COLLISION).getValue()) {
            return false;
        }
        return this.collidedSoftly;
    }
}
