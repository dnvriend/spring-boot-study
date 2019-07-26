package com.github.dnvriend.config;

import com.github.dnvriend.converters.PersonDataToPersonEntityConverter;
import com.github.dnvriend.converters.PersonEntityToPersonDataConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new PersonDataToPersonEntityConverter());
        registry.addConverter(new PersonEntityToPersonDataConverter());
    }
}
