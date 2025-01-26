package io.github.hyper1423.physicscustomizer.commands.pc;

import com.mojang.brigadier.builder.ArgumentBuilder;
import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.commands.Subcommand;
import io.github.hyper1423.physicscustomizer.commands.argument.TypedArgumentUtils;
import io.github.hyper1423.physicscustomizer.config.entry.ConfigEntry;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class ConfigSubcommand implements Subcommand<FabricClientCommandSource> {
    @SuppressWarnings("unchecked")
    @Override
    public ArgumentBuilder<FabricClientCommandSource, ?> buildSubcommand(ArgumentBuilder<FabricClientCommandSource, ?> argBuilder) {
        var tempArgBuilder = argBuilder;
        for (var key : PhysicsCustomizerClient.getPhysicsConfig().getKeys()) {
            String s = key.name();
            var cfg = PhysicsCustomizerClient.getPhysicsConfig();
            var entry = cfg.getEntry(key);

            tempArgBuilder = tempArgBuilder.then(literal(s)
                    .executes((context) -> {
                        String fmt = entry.getValueAsString();
                        context.getSource().sendFeedback(
                                Text.literal("Current value of %s is: %s".formatted(s, fmt))
                        );
                        return 1;
                    })
                    .then(argument("value", TypedArgumentUtils.argumentTypeOf(entry))
                            .executes((context) -> {
                                Object value = TypedArgumentUtils.fetchTypedValueFromContext(context, "value", entry);
                                // Gotta do some unchecked cast because of type erasure :(
                                // We don't know the type of key, but we do know it corresponds to the entry of the same type
                                ((ConfigEntry<Object>) entry).setValue(value);
                                context.getSource().sendFeedback(
                                        Text.literal("Set %s to: %s".formatted(s, entry.getValueAsString()))
                                );
                                return 1;
                            })
                    )
            );
        }
        return tempArgBuilder;
    }
}
