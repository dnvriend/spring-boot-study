package com.github.dnvriend.converters;

import org.springframework.core.convert.converter.Converter;

public class StringToIntConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        return Integer.parseInt(source);
    }
}
