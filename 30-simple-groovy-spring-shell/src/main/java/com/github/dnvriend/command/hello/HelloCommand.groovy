package com.github.dnvriend.command.hello

import groovy.util.logging.Slf4j
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