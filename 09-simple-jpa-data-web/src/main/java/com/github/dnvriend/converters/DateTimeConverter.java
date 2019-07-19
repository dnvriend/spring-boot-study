package com.github.dnvriend.converters;

import org.joda.time.DateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;
import java.util.Optional;

@Converter
public class DateTimeConverter implements AttributeConverter<DateTime, String> {
    @Override
    public String convertToDatabaseColumn(DateTime attribute) {
        return Optional.ofNullable(attribute)
                .map(Objects::toString)
                .orElse(null);
    }

    @Override
    public DateTime convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(DateTime::parse)
                .orElse(null);
    }
}