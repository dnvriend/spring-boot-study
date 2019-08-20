package com.github.dnvriend.controllers;

import com.github.dnvriend.repositories.PersonEntity;
import com.github.dnvriend.repositories.PersonRepository;
import com.github.dnvriend.status.ResourceNotFoundException;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;

    private final ConversionService conversionService;

    public PersonController(PersonRepository personRepository,
        ConversionService conversionService) {
        this.personRepository = personRepository;
        this.conversionService = conversionService;
    }

    @GetMapping("/{userId}")
    public PersonData getPerson(@PathVariable("userId") Long userId) {
        return personRepository.findPersonById(userId)
            .map(p -> conversionService.convert(p, PersonData.class))
            .getOrElseThrow(
                () -> new ResourceNotFoundException(String.format("User with id %s", userId)));
    }

    @GetMapping
    public Seq<PersonData> listPerson() {
        return List.ofAll(personRepository.findAll())
            .map(p -> conversionService.convert(p, PersonData.class));
    }

    @GetMapping("/name/{name}")
    public Seq<PersonData> findByName(@PathVariable("name") String name) {
        return personRepository.findByName(name)
            .map(p -> conversionService.convert(p, PersonData.class));
    }

    @GetMapping("/age/{age}")
    public Seq<PersonData> findByAge(@PathVariable("age") Integer age) {
        return personRepository.findByAge(age)
            .map(p -> conversionService.convert(p, PersonData.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putPerson(@RequestBody @NonNull PersonData person) {
        personRepository.save(conversionService.convert(person, PersonEntity.class));
    }
}
