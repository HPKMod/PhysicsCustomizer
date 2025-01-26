package io.github.hyper1423.physicscustomizer.config;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONConfigLoader implements ConfigLoader {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private final Path directory;
    public JSONConfigLoader(Path directory) {
        this.directory = directory;
    }

    @Override
    public boolean create(String name) throws IOException {
        Path path = directory.resolve(name);
        if (!Files.exists(path)) {
            Files.createFile(path);
            return true;
        }
        return false;
    }

    @Override
    public void write(Config config, String name) throws IOException {
        Path path = directory.resolve(name);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            JsonObject root = new JsonObject();

            for (var key: config.getKeys()) {
                var entry = config.getEntry(key);

                JsonElement elem = serialize(entry.getValue());
                root.add(key.name(), elem);
            }

            gson.toJson(root, writer);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void load(Config config, String name) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(directory.resolve(name))) {
            JsonObject root = gson.fromJson(reader, JsonObject.class);

            for (var jsonEntry: root.entrySet()) {
                ConfigKey<?> key = findKeyByName(config, jsonEntry.getKey());
                Object value = deserializeAs(jsonEntry.getValue(), key.type());

                config.getEntry((ConfigKey<Object>) key).setValue(value);
            }
        }
    }

    @Override
    public boolean delete(String name) throws IOException {
        Path path = directory.resolve(name);
        return Files.deleteIfExists(path);
    }

    @Override
    public boolean exists(String name) {
        return Files.exists(directory.resolve(name));
    }

    @Override
    public String getReadableDestination() {
        return directory.toString();
    }

    private JsonElement serialize(Object value) {
        if (value instanceof Boolean b) {
            return new JsonPrimitive(b);
        } else if (value instanceof Integer i) {
            return new JsonPrimitive(i);
        } else if (value instanceof Double d) {
            return new JsonPrimitive(d);
        } else if (value instanceof String s) {
            return new JsonPrimitive(s);
        } else if (value instanceof WallCollisionRule wcr) {
            return new JsonPrimitive(wcr.asString());
        } else if (value instanceof BlipFallbackMode bfm) {
            return new JsonPrimitive(bfm.asString());
        }

        throw new UnsupportedOperationException("Type %s not supported".formatted(value.getClass()));
    }

    private <T> Object deserializeAs(JsonElement json, Class<T> type) {
        if (type == Boolean.class) {
            return json.getAsBoolean();
        } else if (type == Integer.class) {
            return json.getAsInt();
        } else if (type == Double.class) {
            return json.getAsDouble();
        } else if (type == String.class) {
            return json.getAsString();
        } else if (type == WallCollisionRule.class) {
            return WallCollisionRule.fromString(json.getAsString());
        } else if (type == BlipFallbackMode.class) {
            return BlipFallbackMode.fromString(json.getAsString());
        }

        throw new UnsupportedOperationException("Type %s not supported".formatted(type));
    }

    private ConfigKey<?> findKeyByName(Config config, String name) {
        return config.getKeys().stream().filter((v) -> v.name().equals(name)).findFirst().orElseThrow(
                () -> new IllegalArgumentException("Cannot find a key by name: %s".formatted(name))
        );
    }
}
