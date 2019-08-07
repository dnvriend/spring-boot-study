package com.github.dnvriend.command.translation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
public class TranslationService {
    public String translate(String text, Locale from, Locale to) {
        log.debug("Translating text={}, from={}, to={}", text, from, to);
        return String.format("Hi there! You want to translate %s, from %s to %s, but I have no implementation to do that", text, from, to);
    }
}
