package com.github.dnvriend.controllers;

import com.github.dnvriend.repositories.Person;
import com.github.dnvriend.repositories.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/person")
    public List<Person> getPeople() {
        return personRepository.getPeople().collect(Collectors.toList());
    }

    @GetMapping("/person/{id}")
    public Optional<Person> getPersonById(@PathVariable("id") int id) {
        return personRepository.getPersonById(id);
    }

    @GetMapping("/person/{name}")
    public Stream<Person> getPeopleByName(@PathVariable("name") String name) {
        return personRepository.getPeopleByName(name);
    }

    @GetMapping("/person/{age}")
    public Stream<Person> getPeopleByAge(@PathVariable("age") int age) {
        return personRepository.getPeopleByAge(age);
    }
}
