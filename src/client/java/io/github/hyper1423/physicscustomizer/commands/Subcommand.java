package io.github.hyper1423.physicscustomizer.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;

public interface Subcommand<T extends CommandSource> {
    ArgumentBuilder<T, ?> buildSubcommand(ArgumentBuilder<T, ?> argBuilder);
}
