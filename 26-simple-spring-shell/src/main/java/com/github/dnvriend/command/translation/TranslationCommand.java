package com.github.dnvriend.command.translation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Locale;

@Slf4j
@ShellComponent
public class TranslationCommand {
    private final TranslationService service;

    public TranslationCommand(TranslationService service) {
        this.service = service;
    }

    @ShellMethod("Translate text from one language to another.")
    public String translate(
            @ShellOption() String text,
            @ShellOption(defaultValue = "en_US") Locale from,
            @ShellOption() Locale to) {
        log.debug("Received command: text={}, from={}, to={}", text, from, to);
        return service.translate(text, from, to);
    }
}