package com.github.dnvriend.controllers;

import com.github.dnvriend.status.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/person")
public class PersonController {
    Map<Integer, Person> people = new ConcurrentHashMap<>();

    @GetMapping("/{userId}")
    public Person getPerson(@PathVariable("userId") int userId) {
        return Optional
                .ofNullable(people.get(userId))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %s", userId)));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putPerson(@RequestBody @NonNull Person person) {
        people.put(person.id, person);
    }
}
