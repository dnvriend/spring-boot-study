package com.github.dnvriend.converters;

import java.util.Objects;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.joda.time.Period;

@Converter
public class PeriodAttributeConverter implements AttributeConverter<Period, String> {

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