package com.github.dnvriend.converter;

import java.util.Base64;
import org.springframework.core.convert.converter.Converter;

public class Base64DataToBytesConverter implements Converter<Base64Data, byte[]> {

    @Override
    public byte[] convert(Base64Data source) {
        return Base64.getDecoder().decode(source.getBase64());
    }
}
