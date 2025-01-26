package io.github.hyper1423.physicscustomizer.commands.pc;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.hyper1423.physicscustomizer.commands.Subcommand;
import io.github.hyper1423.physicscustomizer.config.Config;
import io.github.hyper1423.physicscustomizer.config.ConfigLoader;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.io.IOException;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class PresetSubcommand<T extends Config> implements Subcommand<FabricClientCommandSource> {
    private final ConfigLoader loader;
    private final Class<T> configType;
    private final T config;
    public PresetSubcommand(ConfigLoader loader, Class<T> configType, T config) {
        this.loader = loader;
        this.configType = configType;
        this.config = config;
    }

    @Override
    public ArgumentBuilder<FabricClientCommandSource, ?> buildSubcommand(ArgumentBuilder<FabricClientCommandSource, ?> argBuilder) {
        return argBuilder
                .then(literal("create")
                        .then(argument("presetName", StringArgumentType.string())
                                .executes((ctx) -> {
                                    String presetName = StringArgumentType.getString(ctx, "presetName");
                                    if (!presetName.endsWith(".json")) { presetName += ".json"; }

                                    try {
                                        if (!loader.create(presetName)) {
                                            ctx.getSource().sendFeedback(Text.literal(
                                                    ("Configuration preset %s/%s already exists.\n" +
                                                            "If you want to save to an existing file, use 'save' instead of 'create'.")
                                                            .formatted(loader.getReadableDestination(), presetName)
                                            ).withColor(Colors.LIGHT_RED));
                                            return 0;
                                        }
                                        ctx.getSource().sendFeedback(Text.literal(
                                                "Successfully created a new configuration %s/%s."
                                                        .formatted(loader.getReadableDestination(), presetName)));
                                        return 1;
                                    } catch (IOException e) {
                                        String msg = "Failed to create a new configuration %s/%s.".formatted(loader.getReadableDestination(), presetName);
                                        throw new IllegalStateException(msg, e);
                                    }
                                })
                        )
                )
                .then(literal("save")
                        .then(argument("presetName", StringArgumentType.string())
                                .executes((ctx) -> {
                                    String presetName = StringArgumentType.getString(ctx, "presetName");
                                    if (!presetName.endsWith(".json")) { presetName += ".json"; }

                                    if (!loader.exists(presetName)) {
                                        ctx.getSource().sendFeedback(Text.literal(
                                                ("Configuration preset %s/%s does not exist.\n" +
                                                        "If you want to create a new file, use 'create' instead of 'save'.")
                                                        .formatted(loader.getReadableDestination(), presetName)
                                        ).withColor(Colors.LIGHT_RED));
                                        return 0;
                                    }

                                    try {
                                        loader.write(config, presetName);
                                        ctx.getSource().sendFeedback(Text.literal(
                                                "Successfully saved current configuration to %s/%s."
                                                        .formatted(loader.getReadableDestination(), presetName)));
                                    } catch (IOException e) {
                                        throw new IllegalStateException(
                                                "Failed to write configuration to %s/%s."
                                                        .formatted(loader.getReadableDestination(), presetName), e);
                                    }
                                    return 1;
                                })
                        )
                )
                .then(literal("load")
                        .then(argument("presetName", StringArgumentType.string())
                                .executes((ctx) -> {
                                    String presetName = StringArgumentType.getString(ctx, "presetName");
                                    if (!presetName.endsWith(".json")) { presetName += ".json"; }

                                    if (!loader.exists(presetName)) {
                                        ctx.getSource().sendFeedback(Text.literal(
                                                "Configuration preset %s/%s does not exist."
                                                        .formatted(loader.getReadableDestination(), presetName)
                                        ).withColor(Colors.LIGHT_RED));
                                        return 0;
                                    }

                                    try {
                                        loader.load(config, presetName);
                                        ctx.getSource().sendFeedback(Text.literal(
                                                "Successfully loaded configuration from %s/%s."
                                                        .formatted(loader.getReadableDestination(), presetName)));
                                    } catch (IOException e) {
                                        throw new IllegalStateException(
                                                "Failed to load configuration from %s/%s."
                                                        .formatted(loader.getReadableDestination(), presetName), e);
                                    }
                                    return 1;
                                })
                        )
                );
        // No delete here, the user has to manually do it
    }
}
