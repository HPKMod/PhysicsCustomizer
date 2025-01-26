package io.github.hyper1423.physicscustomizer.config.entry;

public class BooleanConfigEntry implements ConfigEntry<Boolean> {
    private Boolean value;

    public BooleanConfigEntry(Boolean defaultValue) {
        this.value = defaultValue;
    }

    @Override
    public Boolean parseValue(String input) throws IllegalArgumentException {
        if (input.equalsIgnoreCase("true")) {
            return true;
        } else if (input.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new IllegalArgumentException("Value must be 'true' or 'false'");
        }
    }

    @Override
    public String getValueAsString() {
        return value.toString();
    }

    @Override
    public void setValue(Boolean newValue) {
        this.value = newValue;
    }
    @Override
    public Boolean getValue() {
        return this.value;
    }
}