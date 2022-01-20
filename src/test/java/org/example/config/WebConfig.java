package org.example.config;

import java.time.Duration;

public class WebConfig {
    private static final Duration DEFAULT_TIMEOUT_SECONDS = Duration.ofSeconds(
            Integer.parseInt(
                    ConfProperties.getProperty("wait.seconds")
            )
    );

    public static Duration getWaitTimeout() {
        return DEFAULT_TIMEOUT_SECONDS;
    }


}
