package com.github.dnvriend.controllers;

import com.github.dnvriend.status.ResourceNotFoundException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
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

    Map<Integer, Person> people = new ConcurrentHashMap<>();

    @GetMapping("/{userId}")
    public Person getPerson(@PathVariable("userId") int userId) {
        return Optional
            .ofNullable(people.get(userId))
            .orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with id %s", userId)));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putPerson(@RequestBody @NonNull Person person) {
        people.put(person.id, person);
    }
}
