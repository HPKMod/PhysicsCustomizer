package io.github.hyper1423.physicscustomizer.config.entry;

public class IntegerConfigEntry extends NumericConfigEntry<Integer> {
    public IntegerConfigEntry(int defaultValue) {
        this(defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    public IntegerConfigEntry(int defaultValue, int min, int max) {
        super(defaultValue, min, max);
    }

    @Override
    public Integer parseValue(String input) throws IllegalArgumentException {
        int inputInteger;
        try {
            inputInteger = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid int: %s".formatted(input), e);
        }

        validateValue(inputInteger);

        return inputInteger;
    }
}