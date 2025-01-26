package io.github.hyper1423.physicscustomizer.commands.utils;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public record EnumSuggestionProvider(
        Collection<String> values
) implements SuggestionProvider<FabricClientCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(
            CommandContext<FabricClientCommandSource> context,
            SuggestionsBuilder builder
    ) throws CommandSyntaxException {
        for (String value : this.values()) {
            builder.suggest(value);
        }

        return builder.buildFuture();
    }
}
