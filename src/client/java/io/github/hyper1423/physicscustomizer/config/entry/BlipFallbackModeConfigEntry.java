package io.github.hyper1423.physicscustomizer.config.entry;

import io.github.hyper1423.physicscustomizer.config.BlipFallbackMode;

public class BlipFallbackModeConfigEntry extends EnumConfigEntry<BlipFallbackMode> {
    public BlipFallbackModeConfigEntry(BlipFallbackMode defaultValue) {
        super(defaultValue);
    }
    @Override
    public String getValueAsString() {
        return value.asString();
    }
}