package com.github.dnvriend.converter;

import java.util.Objects;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.joda.time.Interval;

@Converter
public class IntervalAttributeConverter implements AttributeConverter<Interval, String> {

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