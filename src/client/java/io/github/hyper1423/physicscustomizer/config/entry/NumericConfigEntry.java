package io.github.hyper1423.physicscustomizer.config.entry;

public abstract class NumericConfigEntry<T extends Number & Comparable<T>> implements ConfigEntry<T> {
    protected T value;
    protected final T min;
    protected final T max;

    public NumericConfigEntry(T defaultValue, T min, T max) {
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        validateValue(value);
    }

    protected void validateValue(T value) {
        if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
            throw new IllegalArgumentException("Value " + value + " is out of range: [" + min + ", " + max + "]");
        }
    }

    @Override
    public abstract T parseValue(String input) throws IllegalArgumentException;

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }

    @Override
    public void setValue(T newValue) {
        validateValue(newValue);
        value = newValue;
    }

    @Override
    public T getValue() {
        return value;
    }

    public T min() {
        return min;
    }
    public T max() {
        return max;
    }
}
