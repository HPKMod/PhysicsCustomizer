package io.github.hyper1423.physicscustomizer.commands.pc;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import io.github.hyper1423.physicscustomizer.commands.Subcommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;

public class TestSubcommand implements Subcommand<FabricClientCommandSource> {
    @Override
    public ArgumentBuilder<FabricClientCommandSource, ?> buildSubcommand(ArgumentBuilder<FabricClientCommandSource, ?> argBuilder) {
        return argBuilder
                .executes((context) -> {
                    context.getSource().sendFeedback(Text.literal("Heyyyyyyyy"));
                    return 1;
                })
                .then(argument("foo", DoubleArgumentType.doubleArg())
                        .executes((context) -> {
                            final double fooArg = DoubleArgumentType.getDouble(context, "foo");
                            context.getSource().sendFeedback(Text.literal("당신은 %s만큼 UPT입니다.".formatted(fooArg*fooArg)));
                            return 1;
                        })
                );
    }
}
