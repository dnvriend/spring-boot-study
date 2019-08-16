package com.github.dnvriend.command.hello

import lombok.extern.slf4j.Slf4j
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@Slf4j
@ShellComponent
class HelloCommand {
    @ShellMethod("Say Hello")
    String sayHello() {
        log.debug("Saying Hello")
        "Hello"
    }
}