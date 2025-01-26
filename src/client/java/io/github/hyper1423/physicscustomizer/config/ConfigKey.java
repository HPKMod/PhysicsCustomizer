package io.github.hyper1423.physicscustomizer.config;

public record ConfigKey<T>(String name, Class<T> type) {}