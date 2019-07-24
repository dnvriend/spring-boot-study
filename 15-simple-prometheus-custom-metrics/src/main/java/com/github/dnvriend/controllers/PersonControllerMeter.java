package com.github.dnvriend.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/person")
public class PersonControllerMeter {
    private Map<Integer, Person> people = new ConcurrentHashMap<>();

    private final MeterRegistry registry;

    public PersonControllerMeter(MeterRegistry registry) {
        this.registry = registry;

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Person> getPersons() {
        registry.counter("person_counter_get").increment();
        return people.values();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Person getPerson(@PathVariable("userId") int userId) {
        registry.counter("person_counter_get_by_id").increment();
        return Optional
                .ofNullable(people.get(userId))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %s", userId)));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putPerson(@RequestBody @NonNull Person person) {
        registry.counter("person_counter_put").increment();
        people.put(person.getId(), person);
    }
}
