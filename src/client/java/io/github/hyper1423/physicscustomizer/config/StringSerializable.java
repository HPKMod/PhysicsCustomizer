package io.github.hyper1423.physicscustomizer.config;

public interface StringSerializable {
    String asString();

    static <T> T fromString(String value) {
        return null;
    }
}
