package io.github.hyper1423.physicscustomizer.config;

import net.minecraft.util.StringIdentifiable;

public enum WallCollisionRule implements StringIdentifiable {
    /**
     * Check collisions for the faster axis first, then the slower axis.
     * <p>
     * This means that the faster axis becomes X-facing and the slower axis becomes Z-facing,
     * which is the default behavior for versions >= 1.14.
     */
    FASTER_THEN_SLOWER,

    /**
     * Check collisions for the slower axis first, then the faster axis.
     * <p>
     * This means that the slower axis becomes X-facing and the slower axis becomes Z-facing.
     */
    SLOWER_THEN_FASTER,

    /**
     * Check collisions for the X axis first, then the Z axis.
     * <p>
     * This means that the X axis becomes X-facing and the Z axis becomes Z-facing,
     * which is the default behavior for versions < 1.14, including 1.8.
     */
    X_THEN_Z,

    /**
     * Check collisions for the Z axis first, then the X axis.
     * <p>
     * This means that the Z axis becomes X-facing and the X axis becomes Z-facing.
     */
    Z_THEN_X,

    /**
     * Run 2 collision tests with X_THEN_Z and Z_THEN_X, then check if at least one tests had collisions.
     * <p>
     * This means that every axis becomes X-facing.
     */
    X_AND_Z,

    /**
     * Run 2 collision tests with X_THEN_Z and Z_THEN_X, then check if all tests had collisions.
     * <p>
     * This means that every axis becomes Z-facing.
     */
    X_OR_Z;

    @Override
    public String asString() {
        return this.name().toLowerCase();
    }

    public static WallCollisionRule fromString(String string) {
        return WallCollisionRule.valueOf(string.toUpperCase());
    }
}
