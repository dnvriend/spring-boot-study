package com.github.dnvriend.controllers;

import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Person> getPersons() {
        return Collections.singletonList(Person.builder().id(1).name("dnvriend").age(42).build());
    }
}
