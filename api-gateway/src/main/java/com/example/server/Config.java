package com.example.server;

import java.util.Optional;

/**
 * Created by hyleung on 2016-03-25.
 */
public class Config {
    public static final String SCRIBE_HOST = "scribeHost";
    public static final String SCRIBE_PORT = "scribePort";
    public Optional<String> scribeHost() {
        return Optional.ofNullable(System.getProperty(SCRIBE_HOST, null));
    }

    public Optional<Integer> scribePort() {
        String scribePort = System.getProperty(SCRIBE_PORT, "9410");
        if (scribePort != null) {
            return Optional.of(Integer.parseInt(scribePort));
        }
        return Optional.empty();
    }
}
