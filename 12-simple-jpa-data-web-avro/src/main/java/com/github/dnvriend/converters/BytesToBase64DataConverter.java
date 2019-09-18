package com.github.dnvriend.converter;

import java.util.Base64;
import org.springframework.core.convert.converter.Converter;

public class BytesToBase64DataConverter implements Converter<byte[], Base64Data> {

    @Override
    public Base64Data convert(byte[] source) {
        return Base64Data
            .builder()
            .base64(Base64.getEncoder().encodeToString(source))
            .build();
    }
}
