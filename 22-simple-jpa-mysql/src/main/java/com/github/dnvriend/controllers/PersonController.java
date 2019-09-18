package com.github.dnvriend.controller;

import com.github.dnvriend.repository.Person;
import com.github.dnvriend.repository.PersonRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/person")
    public List<Person> listPersons() {
        return personRepository.findAll();
    }

    @PutMapping("/person")
    public Person putPerson(@RequestBody Person person) {
        return personRepository.save(
            person.toBuilder()
                .createdAt(LocalDateTime.now())
                .build()
        );
    }
}
