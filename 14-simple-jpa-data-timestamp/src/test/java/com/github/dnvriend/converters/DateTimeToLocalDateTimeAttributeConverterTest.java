package com.github.dnvriend.converters;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.val;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Note: LocalDate and LocalDateTime do not contain information about timezone nor time offset.
 * Which means, in order to do something with these objects, you have to pass a ZoneId.
 */
class DateTimeToLocalDateTimeAttributeConverterTest {

    DateTimeToLocalDateTimeAttributeConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DateTimeToLocalDateTimeAttributeConverter();
    }

    @Test
    void given_DateTime_convert_to_LocalDateTime() {
        val dt = DateTime.parse("2019-01-01T10:00:00Z");
        val expected = dt.getMillis();
        val ldt = converter.convertToDatabaseColumn(dt);
        assertThat(ldt).isNotNull();
        long actual = ldt.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void given_LocalDateTime_convert_to_DateTime() {
        val ldt = LocalDateTime.parse("2019-01-01T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME);
        val expected = ldt.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
        val dt = converter.convertToEntityAttribute(ldt);
        val actual = dt.getMillis();
        assertThat(actual).isEqualTo(expected);
    }
}
