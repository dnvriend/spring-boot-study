package com.github.dnvriend.command.translation

import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class TranslationService {
    String translate(String text, Locale from, Locale to) {
        log.debug("Translating text={}, from={}, to={}", text, from, to)
        String.format("Hi there! You want to translate %s, from %s to %s, but I have no implementation to do that", text, from, to)
    }
}
