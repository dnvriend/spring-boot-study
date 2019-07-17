package com.github.dnvriend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableAutoConfiguration
class ConverstionTest {

    @SpringBootConfiguration
    static class MyConfig {

    }

    @Autowired
    private ConversionService conversionService;

    @Test
    void whenConvertStringToIntegerUsingDefaultConverter_thenSuccess() {
        assertThat(conversionService.convert("25", Integer.class)).isEqualTo(25);
    }
}
