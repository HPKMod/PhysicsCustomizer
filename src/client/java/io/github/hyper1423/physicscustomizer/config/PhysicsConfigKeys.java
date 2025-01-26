package io.github.hyper1423.physicscustomizer.config;

public class PhysicsConfigKeys {
    public static final ConfigKey<Boolean> ALLOW_45_STRAFE =
            new ConfigKey<>("allow_45_strafe", Boolean.class);
    public static final ConfigKey<Boolean> ALLOW_SNEAK_SPRINT =
            new ConfigKey<>("allow_sneak_sprint", Boolean.class);
//    public static final ConfigKey<Boolean> INERTIA_THRESHOLD_NORMALIZED =
//            new ConfigKey<>("inertia_threshold_normalized", Boolean.class);
    public static final ConfigKey<Boolean> ALLOW_BLIP =
            new ConfigKey<>("allow_blip", Boolean.class);
    public static final ConfigKey<Boolean> DELAY_MIDAIR_SPRINT =
            new ConfigKey<>("delay_midair_sprint", Boolean.class);
    public static final ConfigKey<Boolean> EXPAND_SUPPORTING_BLOCK_HITBOX =
            new ConfigKey<>("expand_supporting_block_hitbox", Boolean.class);
    public static final ConfigKey<Boolean> SOUL_SAND_USE_COLLISION_INSTEAD =
            new ConfigKey<>("soul_sand_use_collision_instead", Boolean.class);
    public static final ConfigKey<Boolean> USE_OLD_PANE_HITBOX =
            new ConfigKey<>("use_old_pane_hitbox", Boolean.class);
    public static final ConfigKey<Boolean> THINNER_LADDER =
            new ConfigKey<>("thinner_ladder", Boolean.class);
    public static final ConfigKey<Boolean> ALLOW_CLIMBING_BY_JUMP =
            new ConfigKey<>("allow_climbing_by_jump", Boolean.class);
    public static final ConfigKey<Boolean> NO_DRAG =
            new ConfigKey<>("no_drag", Boolean.class);
    public static final ConfigKey<Boolean> PRE_1_13_SWIMMING =
            new ConfigKey<>("pre_1_13_swimming", Boolean.class);
    public static final ConfigKey<Boolean> DISABLE_SOFT_COLLISION =
            new ConfigKey<>("disable_soft_collision", Boolean.class);

    public static final ConfigKey<Integer> F3_COORDINATES_PRECISION =
            new ConfigKey<>("f3_coordinates_precision", Integer.class);

    public static final ConfigKey<Double> INERTIA_THRESHOLD =
            new ConfigKey<>("inertia_threshold", Double.class);
    public static final ConfigKey<Double> COLLISION_HITBOX_CONTRACTION =
            new ConfigKey<>("collision_hitbox_contraction", Double.class);

    public static final ConfigKey<WallCollisionRule> WALL_COLLISION_RULE =
            new ConfigKey<>("wall_collision_rule", WallCollisionRule.class);
    public static final ConfigKey<BlipFallbackMode> BLIP_FALLBACK_MODE =
            new ConfigKey<>("blip_fallback_mode", BlipFallbackMode.class);
}
