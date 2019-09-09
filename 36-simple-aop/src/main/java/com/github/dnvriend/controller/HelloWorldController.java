package com.github.dnvriend.controller;

import com.github.dnvriend.annotation.LogRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloWorldController {

    @GetMapping("/hello")
    @LogRequest
    public String sayHello() {
        log.debug("Saying hello world!");
        return "Hello World!";
    }
}
