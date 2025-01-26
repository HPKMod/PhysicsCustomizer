package io.github.hyper1423.physicscustomizer.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class TestCommandTree implements ClientCommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
            dispatcher.register(literal("hpktest")
                    .executes(this::runDefault)
                    .then(argument("fooArg", DoubleArgumentType.doubleArg(-100., 100.))
                            .executes(this::foo)
                    )
            );
    }
    private int runDefault(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        context.getSource().sendFeedback(Text.literal("Heyyyyyyyy"));
        return 1;
    }
    private int foo(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        final double fooArg = DoubleArgumentType.getDouble(context, "fooArg");
        context.getSource().sendFeedback(Text.literal("%s * %s = %s".formatted(fooArg, fooArg, fooArg*fooArg)));
        return 1;
    }
}
