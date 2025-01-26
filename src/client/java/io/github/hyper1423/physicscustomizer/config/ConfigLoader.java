package io.github.hyper1423.physicscustomizer.config;

import java.io.IOException;

public interface ConfigLoader {
    boolean create(String name) throws IOException;
    void write(Config config, String name) throws IOException;
    void load(Config config, String name) throws IOException;
    boolean delete(String name) throws IOException;
    boolean exists(String name);

    String getReadableDestination();
}