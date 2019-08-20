package com.github.dnvriend.converters;

import java.util.Objects;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.joda.time.DateTime;

@Converter
public class DateTimeAttributeConverter implements AttributeConverter<DateTime, String> {

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