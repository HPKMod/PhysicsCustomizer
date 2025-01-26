package io.github.hyper1423.physicscustomizer.mixin.client;

import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfigKeys;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {
	@Inject(method = "tick()V", at = @At("TAIL"))
	private void tick(CallbackInfo info) {
		if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.ALLOW_45_STRAFE).getValue()) { return; }

		double lenSqr = movementForward*movementForward + movementSideways*movementSideways;
		if (lenSqr != 0.0) {
			double len = Math.sqrt(lenSqr);
			// 정 상 화
			movementForward /= (float) len;
			movementSideways /= (float) len;
		}
	}
}