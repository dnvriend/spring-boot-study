package com.github.dnvriend.controllers;

import com.github.dnvriend.meterbinder.PersonStatsProbe;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

//@RestController
//@RequestMapping("/person")
public class PersonControllerProbe {
    private Map<Integer, Person> people = new ConcurrentHashMap<>();

    private final PersonStatsProbe personStatsProbe;

    public PersonControllerProbe(PersonStatsProbe personStatsProbe) {
        this.personStatsProbe = personStatsProbe;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Person> getPersons() {
        personStatsProbe.incrementGetPersonCounter();
        return people.values();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Person getPerson(@PathVariable("userId") int userId) {
        personStatsProbe.incrementGetPersonByIdCounter();
        return Optional
                .ofNullable(people.get(userId))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %s", userId)));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putPerson(@RequestBody @NonNull Person person) {
        personStatsProbe.incrementPutPersonCounter();
        people.put(person.getId(), person);
    }
}

