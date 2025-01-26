package io.github.hyper1423.physicscustomizer.config;

import io.github.hyper1423.physicscustomizer.config.entry.ConfigEntry;

import java.util.Map;
import java.util.Set;

public interface Config {
    <T> ConfigEntry<T> getEntry(ConfigKey<T> key);
    Set<ConfigKey<?>> getKeys();

    Map<ConfigKey<?>, ConfigEntry<?>> toUnmodifiableMap();
    void applyMap(Map<ConfigKey<?>, ConfigEntry<?>> map);
}
