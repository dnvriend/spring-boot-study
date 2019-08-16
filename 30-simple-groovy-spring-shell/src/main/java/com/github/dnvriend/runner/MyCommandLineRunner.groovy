package com.github.dnvriend.runner

import lombok.extern.slf4j.Slf4j
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Slf4j
@Component
class MyCommandLineRunner implements CommandLineRunner {
    @Override
    void run(String... args) throws Exception {
        log.debug("Hello World")
    }
}

