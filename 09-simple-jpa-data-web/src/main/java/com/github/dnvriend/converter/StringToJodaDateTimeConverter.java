package com.github.dnvriend.converter;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

public class StringToJodaDateTimeConverter implements Converter<String, DateTime> {

    @Override
    public DateTime convert(String s) {
        return DateTime.parse(s);
    }
}
