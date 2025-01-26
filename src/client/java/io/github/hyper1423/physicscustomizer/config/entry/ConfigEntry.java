package io.github.hyper1423.physicscustomizer.config.entry;

public interface ConfigEntry<T> {
    /**
     * Given a user input (string), parse it into the appropriate type.
     * Throws an exception or returns an error if the value is invalid.
     */
    T parseValue(String input) throws IllegalArgumentException;

    /**
     * Get the current value in a nice string format.
     */
    String getValueAsString();

    /**
     * Set a new value.
     */
    void setValue(T newValue);

    /**
     * Get the current value.
     */
    T getValue();
}
