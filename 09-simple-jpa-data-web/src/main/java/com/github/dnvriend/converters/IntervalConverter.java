package com.github.dnvriend.converters;

import org.joda.time.Interval;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;
import java.util.Optional;

@Converter
public class IntervalConverter implements AttributeConverter<Interval, String> {
    @Override
    public String convertToDatabaseColumn(Interval attribute) {
        return Optional.ofNullable(attribute)
                .map(Objects::toString)
                .orElse(null);
    }

    @Override
    public Interval convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(Interval::parse)
                .orElse(null);
    }
}