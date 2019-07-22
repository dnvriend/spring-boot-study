package com.github.dnvriend.config;

import com.github.dnvriend.converters.StringToJodaDateTimeConverter;
import com.github.dnvriend.converters.TripleToValueConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new TripleToValueConverter());
        registry.addConverter(new StringToJodaDateTimeConverter());
    }
}