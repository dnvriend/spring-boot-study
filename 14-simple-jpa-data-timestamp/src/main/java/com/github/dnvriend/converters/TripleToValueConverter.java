package com.github.dnvriend.converter;

import com.github.dnvriend.controller.Value;
import com.github.dnvriend.repository.Triple;
import org.springframework.core.convert.converter.Converter;

public class TripleToValueConverter implements Converter<Triple, Value> {

    @Override
    public Value convert(Triple triple) {
        return Value.builder()
            .value(triple.getValue())
            .start(triple.getId().getStart())
            .end(triple.getId().getEnd())
            .build();
    }
}
