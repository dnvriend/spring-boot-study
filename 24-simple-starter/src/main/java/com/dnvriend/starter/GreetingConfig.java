package com.dnvriend.starter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class GreetingConfig {
    private String userName;

    private Messages messages;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    public static class Messages {
        private String morningMessage;
        private String afternoonMessage;
        private String eveningMessage;
        private String nightMessage;
    }
}
