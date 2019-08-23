package com.github.dnvriend.controllers;

import com.github.dnvriend.repositories.PersonData;
import com.github.dnvriend.repositories.PersonDataRepository;
import com.github.dnvriend.status.ResourceNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonDataController {

    private final PersonDataRepository repository;

    private final ConversionService conversionService;

    PersonDataController(PersonDataRepository repository, ConversionService conversionService) {
        this.repository = repository;
        this.conversionService = conversionService;
    }

    @GetMapping("/person")
    Page<PersonMvc> findAllPersons(Pageable pageable) {
        return repository.findAll(pageable)
            .map(pd -> conversionService.convert(pd, PersonMvc.class));
    }

    @GetMapping("/person/{userId}")
    PersonMvc getPerson(@PathVariable("userId") Long userId) {
        return repository.findById(userId)
            .map(pd -> conversionService.convert(pd, PersonMvc.class))
            .orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with id %s", userId)));
    }

    @PutMapping("/person")
    void putPerson(@RequestBody PersonMvc person) {
        repository.save(conversionService.convert(person, PersonData.class));
    }
}
