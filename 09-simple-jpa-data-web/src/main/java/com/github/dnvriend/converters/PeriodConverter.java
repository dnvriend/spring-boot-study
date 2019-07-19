package com.github.dnvriend.converters;

import org.joda.time.Period;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;
import java.util.Optional;

@Converter
public class PeriodConverter implements AttributeConverter<Period, String> {
    @Override
    public String convertToDatabaseColumn(Period attribute) {
        return Optional.ofNullable(attribute)
                .map(Objects::toString)
                .orElse(null);
    }

    @Override
    public Period convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(Period::parse)
                .orElse(null);
    }
}