package com.github.dnvriend.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String s) {
        return LocalDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME);
    }
}
