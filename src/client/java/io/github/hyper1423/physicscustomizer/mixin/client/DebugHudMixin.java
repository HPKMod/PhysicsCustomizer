package io.github.hyper1423.physicscustomizer.mixin.client;

import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfigKeys;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {
    @ModifyConstant(
            method = "getLeftText",
            constant = @Constant(stringValue = "XYZ: %.3f / %.5f / %.3f")
    )
    private String changeF3Precision(String str) {
        int precision = PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.F3_COORDINATES_PRECISION).getValue();
        return "XYZ: %%.%df / %%.%df / %%.%df".formatted(precision, precision, precision);
    }
}
