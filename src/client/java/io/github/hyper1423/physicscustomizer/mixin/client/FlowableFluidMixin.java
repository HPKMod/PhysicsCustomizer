package io.github.hyper1423.physicscustomizer.mixin.client;

import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfigKeys;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FlowableFluid.class)
public abstract class FlowableFluidMixin {
//    @Inject(
//            method = "getHeight(Lnet/minecraft/fluid/FluidState;)F",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    private void increaseHeightByOne(FluidState state, CallbackInfoReturnable<Float> cir) {
//        if (PhysicsCustomizerClient.getParkourConfig().entryOf(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
//            cir.setReturnValue((float)(1 + state.getLevel()) / 9.0F);
//        }
//    }

    @Redirect(
            method = "getHeight(Lnet/minecraft/fluid/FluidState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;getHeight()F"
            )
    )
    private float increaseHeightByOneForGetHeight(FluidState state) {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.PRE_1_13_SWIMMING).getValue()) {
            return (float)(1 + state.getLevel()) / 9.0F;
        }
        return state.getHeight();
    }
}
