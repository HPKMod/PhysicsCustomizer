package io.github.hyper1423.physicscustomizer.commands.argument;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.CommandContext;
import io.github.hyper1423.physicscustomizer.config.entry.*;

public class TypedArgumentUtils {
    public static ArgumentType<?> argumentTypeOf(ConfigEntry<?> entry) {
        if (entry instanceof BooleanConfigEntry) {
            return BoolArgumentType.bool();
        } else if (entry instanceof IntegerConfigEntry ie) {
            return IntegerArgumentType.integer(ie.min(), ie.max());
        } else if (entry instanceof DoubleConfigEntry de) {
            return DoubleArgumentType.doubleArg(de.min(), de.max());
        } else if (entry instanceof WallCollisionRuleConfigEntry) {
            return WallCollisionRuleArgumentType.wallCollisionRule();
        } else if (entry instanceof BlipFallbackModeConfigEntry) {
            return BlipFallbackModeArgumentType.blipFallbackMode();
        } else if (entry instanceof WrappedConfigEntry<?,?> e) {
            return argumentTypeOf(e.getInternal());
        }
        throw new UnsupportedOperationException();
    }

    public static Object fetchTypedValueFromContext(CommandContext<?> ctx, String name, ConfigEntry<?> entry) {
        if (entry instanceof BooleanConfigEntry) {
            return BoolArgumentType.getBool(ctx, name);
        } else if (entry instanceof IntegerConfigEntry) {
            return IntegerArgumentType.getInteger(ctx, name);
        } else if (entry instanceof DoubleConfigEntry) {
            return DoubleArgumentType.getDouble(ctx, name);
        } else if (entry instanceof WallCollisionRuleConfigEntry) {
            return WallCollisionRuleArgumentType.getWallCollisionRule(ctx, name);
        } else if (entry instanceof BlipFallbackModeConfigEntry) {
            return BlipFallbackModeArgumentType.getBlipFallbackMode(ctx, name);
        } else if (entry instanceof WrappedConfigEntry<?,?> e) {
            return fetchTypedValueFromContext(ctx, name, e.getInternal());
        }
        throw new UnsupportedOperationException();
    }
}
