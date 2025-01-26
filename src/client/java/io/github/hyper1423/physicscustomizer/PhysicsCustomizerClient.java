package io.github.hyper1423.physicscustomizer;

import io.github.hyper1423.physicscustomizer.commands.CommandInitializer;
import io.github.hyper1423.physicscustomizer.config.Config;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfig;
import io.github.hyper1423.physicscustomizer.render.PhysicsCustomizerModelLoadingPlugin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PhysicsCustomizerClient implements ClientModInitializer {
	// Lazy init
	private static PhysicsConfig PHYSICS_CONFIG = null;
	public static PhysicsConfig getPhysicsConfig() {
		if (PHYSICS_CONFIG == null) {
			PHYSICS_CONFIG = new PhysicsConfig();
		}
		return PHYSICS_CONFIG;
	}

	public Path getConfigRoot() {
		Path path = Paths.get("PhysicsCustomizer");
		if (!Files.exists(path)) {
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				throw new IllegalStateException("Failed to create folder");
			}
		}
		return path;
	}

	@Override
	public void onInitializeClient() {
		PhysicsCustomizer.LOGGER.info("HEYYYY!!!");

		CommandInitializer commandInit = new CommandInitializer(this);
		commandInit.init();
	}
}