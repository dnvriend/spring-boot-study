package com.github.dnvriend.config;

import com.fasterxml.jackson.databind.Module;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonModuleConfig {

    @Bean
    public Module vavrModule() {
        return new VavrModule();
    }
}
