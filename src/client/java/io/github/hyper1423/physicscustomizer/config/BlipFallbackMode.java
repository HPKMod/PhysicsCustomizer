package io.github.hyper1423.physicscustomizer.config;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum BlipFallbackMode implements StringIdentifiable {
    /**
     * Use (current Y + Y distance to the ground) as the fallback value.
     * <p>
     * This is equivalent to not using fallback values. When using this mode, no blip-related glitches happen,
     * which is the default behavior for versions >= 1.20.
     */
    NONE,

    /**
     * Use (current Y + Y velocity) as the fallback value.
     * <p>
     * This causes blip-downs to be possible,
     * which is the default behavior for versions >= 1.14 and < 1.20.
     */
    BLIP_DOWN,

    /**
     * Use player's current Y coordinate as the fallback value.
     * <p>
     * This causes blip-ups to be possible,
     * which is the default behavior for versions < 1.14.
     */
    BLIP_UP;

    @Override
    public String asString() {
        return this.name().toLowerCase();
    }

    public static BlipFallbackMode fromString(String string) {
        return BlipFallbackMode.valueOf(string.toUpperCase());
    }
}
