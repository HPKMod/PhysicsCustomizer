package io.github.hyper1423.physicscustomizer.config.entry;

import java.util.function.Consumer;

public class CallbackWrappedConfigEntry<T, C extends ConfigEntry<T>> extends WrappedConfigEntry<T, C> {
    private final Consumer<T> setterCallback;
    public CallbackWrappedConfigEntry(C configEntry, Consumer<T> setterCallback) {
        super(configEntry);
        this.setterCallback = setterCallback;
    }

    @Override
    public void setValue(T newValue) {
        setterCallback.accept(newValue);
        super.setValue(newValue);
    }
}
