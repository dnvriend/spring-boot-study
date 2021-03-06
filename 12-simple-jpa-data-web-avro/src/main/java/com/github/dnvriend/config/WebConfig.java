package com.github.dnvriend.config;

import com.github.dnvriend.converter.Base64DataToBytesConverter;
import com.github.dnvriend.converter.BytesToBase64DataConverter;
import com.github.dnvriend.converter.PersonDataToPersonMvcConverter;
import com.github.dnvriend.converter.PersonMvcToPersonDataConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new BytesToBase64DataConverter());
        registry.addConverter(new Base64DataToBytesConverter());
        registry.addConverter(new PersonDataToPersonMvcConverter());
        registry.addConverter(new PersonMvcToPersonDataConverter());
    }
}