package com.github.dnvriend.converters;

import org.springframework.core.convert.converter.Converter;

import java.util.Base64;

public class Base64DataToBytesConverter implements Converter<Base64Data, byte[]> {
    @Override
    public byte[] convert(Base64Data source) {
        return Base64.getDecoder().decode(source.getBase64());
    }
}
