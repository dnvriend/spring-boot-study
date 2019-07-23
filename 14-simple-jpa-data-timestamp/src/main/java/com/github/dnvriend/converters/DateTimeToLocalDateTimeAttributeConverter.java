package com.github.dnvriend.converters;

import org.joda.time.DateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Converter
public class DateTimeToLocalDateTimeAttributeConverter implements AttributeConverter<DateTime, LocalDateTime> {
    @Override
    public LocalDateTime convertToDatabaseColumn(DateTime attribute) {
        return Optional.ofNullable(attribute)
                .map(Objects::toString)
                .map(str -> LocalDateTime.parse(str, DateTimeFormatter.ISO_DATE_TIME))
                .orElse(null);
    }

    @Override
    public DateTime convertToEntityAttribute(LocalDateTime dbData) {
        return Optional.ofNullable(dbData)
                .map(data -> data.format(DateTimeFormatter.ISO_DATE_TIME))
                .map(DateTime::parse)
                .orElse(null);
    }
}