package com.github.dnvriend.command.translation

import groovy.util.logging.Slf4j
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@Slf4j
@ShellComponent
class TranslationCommand {
    private final TranslationService service

    TranslationCommand(TranslationService service) {
        this.service = service
    }

    @ShellMethod("Translate text from one language to another.")
    String translate(
            @ShellOption() String text,
            @ShellOption(defaultValue = "en_US") Locale from,
            @ShellOption() Locale to) {
        log.debug("Received command: text={}, from={}, to={}", text, from, to)
        service.translate(text, from, to)
    }
}