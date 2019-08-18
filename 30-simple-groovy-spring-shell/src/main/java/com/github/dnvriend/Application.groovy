package com.github.dnvriend

import groovy.util.logging.Slf4j
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@Slf4j
@SpringBootApplication
class Application {
    static void main(String... args) {
        log.debug("Launching CLI")
        SpringApplication.run(Application, args)
    }
}