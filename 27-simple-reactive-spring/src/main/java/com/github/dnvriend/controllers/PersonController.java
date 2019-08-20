package com.github.dnvriend.controllers;

import com.github.dnvriend.domain.Person;
import com.github.dnvriend.services.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person/{id}")
    public Mono<Person> getPersonById(@PathVariable long id) {
        return personService.findPersonById(id);
    }
}
