package io.github.hyper1423.physicscustomizer.config;

import com.google.common.collect.Sets;
import io.github.hyper1423.physicscustomizer.config.entry.*;
import net.minecraft.client.MinecraftClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PhysicsConfig implements Config {
    private final Map<ConfigKey<?>, ConfigEntry<?>> entries = new HashMap<>();

    public PhysicsConfig() {
        entries.put(PhysicsConfigKeys.ALLOW_45_STRAFE, new BooleanConfigEntry(true));
        entries.put(PhysicsConfigKeys.ALLOW_SNEAK_SPRINT, new BooleanConfigEntry(false));
        entries.put(PhysicsConfigKeys.ALLOW_BLIP, new BooleanConfigEntry(false));
        entries.put(PhysicsConfigKeys.DELAY_MIDAIR_SPRINT, new BooleanConfigEntry(false));
        entries.put(PhysicsConfigKeys.EXPAND_SUPPORTING_BLOCK_HITBOX, new BooleanConfigEntry(true));
        entries.put(PhysicsConfigKeys.SOUL_SAND_USE_COLLISION_INSTEAD, new BooleanConfigEntry(false));
        entries.put(PhysicsConfigKeys.THINNER_LADDER, new BooleanConfigEntry(false));
        entries.put(PhysicsConfigKeys.ALLOW_CLIMBING_BY_JUMP, new BooleanConfigEntry(true));
        entries.put(PhysicsConfigKeys.NO_DRAG, new BooleanConfigEntry(false));
        entries.put(PhysicsConfigKeys.PRE_1_13_SWIMMING, new BooleanConfigEntry(false));
        entries.put(PhysicsConfigKeys.DISABLE_SOFT_COLLISION, new BooleanConfigEntry(false));
        entries.put(PhysicsConfigKeys.USE_OLD_PANE_HITBOX, new CallbackWrappedConfigEntry<>(new BooleanConfigEntry(false),
                (value) -> MinecraftClient.getInstance().reloadResources()
        ));

        entries.put(PhysicsConfigKeys.F3_COORDINATES_PRECISION, new IntegerConfigEntry(5, 0, 32));

        entries.put(PhysicsConfigKeys.INERTIA_THRESHOLD, new DoubleConfigEntry(0.003));
        entries.put(PhysicsConfigKeys.COLLISION_HITBOX_CONTRACTION, new DoubleConfigEntry(1E-5F));

        entries.put(PhysicsConfigKeys.WALL_COLLISION_RULE, new WallCollisionRuleConfigEntry(WallCollisionRule.FASTER_THEN_SLOWER));
        entries.put(PhysicsConfigKeys.BLIP_FALLBACK_MODE, new BlipFallbackModeConfigEntry(BlipFallbackMode.NONE));
    }

    // Ugh, I hate type erasure...
    // We know ConfigKey<T> always corresponds to ConfigEntry<T>, with the same type.
    @SuppressWarnings("unchecked")
    @Override
    public <T> ConfigEntry<T> getEntry(ConfigKey<T> key) {
        var entry = entries.get(key);
        if (entry == null) { throw new IllegalArgumentException("Invalid key"); }

        return (ConfigEntry<T>) entry;
    }

    @Override
    public Set<ConfigKey<?>> getKeys() {
        return Collections.unmodifiableSet(entries.keySet());
    }

    @Override
    public Map<ConfigKey<?>, ConfigEntry<?>> toUnmodifiableMap() {
        return Collections.unmodifiableMap(entries);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void applyMap(Map<ConfigKey<?>, ConfigEntry<?>> map) {
        var keySet = this.getKeys();
        var otherKeySet = map.keySet();
        if (!keySet.containsAll(otherKeySet)) {
            var invalidKeys = Sets.difference(otherKeySet, keySet);
            throw new IllegalArgumentException("Argument contains invalid key(s): %s".formatted(invalidKeys));
        }

        for (var key: keySet) {
            var newValue = map.get(key);
            this.getEntry((ConfigKey<Object>) key).setValue(newValue);
        }
    }
}
