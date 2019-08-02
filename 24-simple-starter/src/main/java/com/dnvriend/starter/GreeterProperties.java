package com.dnvriend.starter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.dnvriend.starter.GreeterConstants.*;

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
    private String userName = USER_NAME;
    /**
     * A morning message
     */
    private String morningMessage = MORNING_MESSAGE;
    /**
     * An afternoon message
     */
    private String afternoonMessage = AFTERNOON_MESSAGE;
    /**
     * An evening message
     */
    private String eveningMessage = EVENING_MESSAGE;
    /**
     * A night message
     */
    private String nightMessage = NIGHT_MESSAGE;
}
