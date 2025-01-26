package io.github.hyper1423.physicscustomizer.commands.argument;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.Codec;
import io.github.hyper1423.physicscustomizer.config.WallCollisionRule;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public class WallCollisionRuleArgumentType extends EnumArgumentType<WallCollisionRule> {
    private static final Codec<WallCollisionRule> CODEC = StringIdentifiable.createCodec(
            WallCollisionRuleArgumentType::getValues, name -> name.toLowerCase(Locale.ROOT)
    );

    private WallCollisionRuleArgumentType() {
        super(CODEC, WallCollisionRuleArgumentType::getValues);
    }

    private static WallCollisionRule[] getValues() {
        return WallCollisionRule.values();
    }

    public static WallCollisionRule getWallCollisionRule(CommandContext<?> context, String id) {
        return context.getArgument(id, WallCollisionRule.class);
    }

    public static WallCollisionRuleArgumentType wallCollisionRule() {
        return new WallCollisionRuleArgumentType();
    }

    @Override
    protected String transformValueName(String name) {
        return name.toLowerCase(Locale.ROOT);
    }
}
