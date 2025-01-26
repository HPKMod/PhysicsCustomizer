package io.github.hyper1423.physicscustomizer.commands.argument;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.Codec;
import io.github.hyper1423.physicscustomizer.config.BlipFallbackMode;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public class BlipFallbackModeArgumentType extends EnumArgumentType<BlipFallbackMode> {
    private static final Codec<BlipFallbackMode> CODEC = StringIdentifiable.createCodec(
            BlipFallbackModeArgumentType::getValues, name -> name.toLowerCase(Locale.ROOT)
    );

    private BlipFallbackModeArgumentType() {
        super(CODEC, BlipFallbackModeArgumentType::getValues);
    }

    private static BlipFallbackMode[] getValues() {
        return BlipFallbackMode.values();
    }

    public static BlipFallbackMode getBlipFallbackMode(CommandContext<?> context, String id) {
        return context.getArgument(id, BlipFallbackMode.class);
    }

    public static BlipFallbackModeArgumentType blipFallbackMode() {
        return new BlipFallbackModeArgumentType();
    }

    @Override
    protected String transformValueName(String name) {
        return name.toLowerCase(Locale.ROOT);
    }
}
