package io.github.hyper1423.physicscustomizer.commands;

import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.commands.pc.PhysicsCustomizerCommandTree;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class CommandInitializer {
    private final PhysicsCustomizerClient mod;
    public CommandInitializer(PhysicsCustomizerClient mod) {
        this.mod = mod;
    }

    public void init() {
        ClientCommandRegistrationCallback.EVENT.register(new TestCommandTree());
        ClientCommandRegistrationCallback.EVENT.register(new PhysicsCustomizerCommandTree(mod));
    }
}
