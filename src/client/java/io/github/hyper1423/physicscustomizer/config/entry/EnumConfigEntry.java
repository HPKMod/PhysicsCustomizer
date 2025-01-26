package io.github.hyper1423.physicscustomizer.config.entry;

public abstract class EnumConfigEntry<T extends Enum<T>> implements ConfigEntry<T> {
    protected T value;

    public EnumConfigEntry(T defaultValue) {
        this.value = defaultValue;
    }

    @Override
    // Enum.valueOf does not know that the enum is actually in the type of T. (Yeah, the compiler is not exactly... smart)
    // We do know this, so we use a SuppressWarnings here.
    @SuppressWarnings("unchecked")
    public T parseValue(String input) throws IllegalArgumentException {
        return (T) Enum.valueOf(value.getClass(), input);
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }

    @Override
    public void setValue(T newValue) {
        this.value = newValue;
    }
    @Override
    public T getValue() {
        return this.value;
    }
}