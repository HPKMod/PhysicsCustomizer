package io.github.hyper1423.physicscustomizer;

import io.github.hyper1423.physicscustomizer.commands.CommandInitializer;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfig;
import net.fabricmc.api.ClientModInitializer;

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
		PhysicsCustomizer.LOGGER.info("Initialize PhysicsCustomizer v0.0.1");

		CommandInitializer commandInit = new CommandInitializer(this);
		commandInit.init();
	}
}