package com.github.dnvriend.config;

import com.github.dnvriend.meterbinder.PersonStatsProbe;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ActuatorConfig {
    @Bean
    public PersonStatsProbe dataSourceStatusProbe() {
        return new PersonStatsProbe();
    }
}