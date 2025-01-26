package io.github.hyper1423.physicscustomizer.config.entry;

import io.github.hyper1423.physicscustomizer.config.WallCollisionRule;

// Maybe a marking class? IDK, haha
public class WallCollisionRuleConfigEntry extends EnumConfigEntry<WallCollisionRule> {
    public WallCollisionRuleConfigEntry(WallCollisionRule defaultValue) {
        super(defaultValue);
    }
    @Override
    public String getValueAsString() {
        return value.asString();
    }
}