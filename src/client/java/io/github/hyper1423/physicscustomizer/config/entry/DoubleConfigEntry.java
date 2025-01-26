package io.github.hyper1423.physicscustomizer.config.entry;

public class DoubleConfigEntry extends NumericConfigEntry<Double> {
    public DoubleConfigEntry(double defaultValue) {
        this(defaultValue, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    public DoubleConfigEntry(double defaultValue, double min, double max) {
        super(defaultValue, min, max);
    }

    @Override
    public Double parseValue(String input) throws IllegalArgumentException {
        double inputDouble;
        try {
            inputDouble = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid double: %s".formatted(input), e);
        }

        validateValue(inputDouble);

        return inputDouble;
    }
}