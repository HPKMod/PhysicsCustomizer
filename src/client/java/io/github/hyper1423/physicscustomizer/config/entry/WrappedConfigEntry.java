package io.github.hyper1423.physicscustomizer.config.entry;

public class WrappedConfigEntry<T, C extends ConfigEntry<T>> implements ConfigEntry<T> {
    protected final C internal;
    public WrappedConfigEntry(C configEntry) {
        this.internal = configEntry;
    }

    @Override
    public T parseValue(String input) throws IllegalArgumentException {
        return internal.parseValue(input);
    }

    @Override
    public String getValueAsString() {
        return internal.getValueAsString();
    }

    @Override
    public void setValue(T newValue) {
        internal.setValue(newValue);
    }

    @Override
    public T getValue() {
        return internal.getValue();
    }

    public C getInternal() {
        return internal;
    }
}
