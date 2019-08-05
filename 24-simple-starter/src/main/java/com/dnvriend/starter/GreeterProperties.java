package com.dnvriend.starter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ConfigurationProperties(prefix = "com.github.dnvriend.greeter")
public class GreeterProperties {
    /**
     * The annotation processor (pom dependency) will use comments
     * as a description when processing this class
     */
    private String userName = "dnvriend";
    /**
     * A morning message
     */
    private String morningMessage = "Hello %s, it is morning";
    /**
     * An afternoon message
     */
    private String afternoonMessage = "Hello %s, it is afternoon";
    /**
     * An evening message
     */
    private String eveningMessage = "Hello %s, it is evening";
    /**
     * A night message
     */
    private String nightMessage = "Hello %s, it is night";
}
