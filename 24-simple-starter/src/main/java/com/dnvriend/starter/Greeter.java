package com.dnvriend.starter;

import java.time.LocalDateTime;

public class Greeter {

    private final GreetingConfig greetingConfig;

    public Greeter(GreetingConfig greetingConfig) {
        this.greetingConfig = greetingConfig;
    }

    public String greet() {
        int hourOfDay = LocalDateTime.now().getHour();
        if (hourOfDay >= 5 && hourOfDay < 12) {
            return String.format(greetingConfig.getMorningMessage(), greetingConfig.getUserName());
        } else if (hourOfDay >= 12 && hourOfDay < 17) {
            return String
                .format(greetingConfig.getAfternoonMessage(), greetingConfig.getUserName());
        } else if (hourOfDay >= 17 && hourOfDay < 20) {
            return String.format(greetingConfig.getEveningMessage(), greetingConfig.getUserName());
        } else {
            return String.format(greetingConfig.getNightMessage(), greetingConfig.getUserName());
        }
    }
}
