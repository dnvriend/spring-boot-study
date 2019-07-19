package com.github.dnvriend.config;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.cfg.FormatConfig;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.deser.IntervalDeserializer;
import com.fasterxml.jackson.datatype.joda.deser.PeriodDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.fasterxml.jackson.datatype.joda.ser.PeriodSerializer;
import com.github.dnvriend.converters.StringToJodaDateTimeConverter;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration
public class JacksonConfig implements WebMvcConfigurer {
    SimpleModule configureSimpleModule(final SimpleModule simpleModule) {
        JsonDeserializer<?> periodDeserializer = new PeriodDeserializer(true);
        JsonDeserializer<?> intervalDeserializer = new IntervalDeserializer(); // should be UTC
        JsonDeserializer<?> dateTimeDeserializer = new DateTimeDeserializer(org.joda.time.DateTime.class, FormatConfig.DEFAULT_LOCAL_DATETIME_PARSER);
        simpleModule.addDeserializer(Period.class, (JsonDeserializer<Period>) periodDeserializer);
        simpleModule.addSerializer(Period.class, new PeriodSerializer());
        simpleModule.addDeserializer(Interval.class, (JsonDeserializer<Interval>) intervalDeserializer);
        simpleModule.addSerializer(org.joda.time.DateTime.class, new DateTimeSerializer());
        simpleModule.addDeserializer(org.joda.time.DateTime.class, (JsonDeserializer<org.joda.time.DateTime>) dateTimeDeserializer);
        return simpleModule;
    }

    Module buildJsonSerializers() {
        return configureSimpleModule(new SimpleModule());
    }

    @Bean
    public ObjectMapper jsonObjectMapper() {
        return new ObjectMapper()
                .findAndRegisterModules()
                .configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(buildJsonSerializers());
    }

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        converters.add(createJsonMessageConverter());
    }

    HttpMessageConverter<?> createJsonMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(
                this.jsonObjectMapper()
        );
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        return converter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToJodaDateTimeConverter());
    }
}