package io.github.hyper1423.physicscustomizer.commands.pc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.commands.Subcommand;
import io.github.hyper1423.physicscustomizer.config.JSONConfigLoader;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class PhysicsCustomizerCommandTree implements ClientCommandRegistrationCallback {
    private final PhysicsCustomizerClient mod;
    public PhysicsCustomizerCommandTree(PhysicsCustomizerClient mod) {
        this.mod = mod;
    }

    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        Subcommand<FabricClientCommandSource> config = new ConfigSubcommand();
        Subcommand<FabricClientCommandSource> preset =
                new PresetSubcommand<>(new JSONConfigLoader(mod.getConfigRoot()), PhysicsConfig.class, PhysicsCustomizerClient.getPhysicsConfig());
        Subcommand<FabricClientCommandSource> test = new TestSubcommand();

        dispatcher.register(literal("pc")
                .then(config.buildSubcommand(literal("config")))
                .then(preset.buildSubcommand(literal("preset")))
                .then(test.buildSubcommand(literal("test")))
        );
    }
}
