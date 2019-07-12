package com.github.dnvriend.controllers;

import com.github.dnvriend.status.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/person")
public class PersonController {

    private final Map<Integer, Person> people;

    private final PersonService personService;

    public PersonController(@NonNull Map<Integer, Person> people,
                            @NonNull PersonService personService) {
        this.people = people;
        this.personService = personService;
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Person getPerson(@PathVariable("userId") @Min(0) int userId) {
        return Optional
                .ofNullable(people.get(userId))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %s", userId)));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putPerson(@Valid @RequestBody @NonNull Person person) {
        people.put(person.getId(), person);
    }

    @PutMapping("/validate")
    public void savePerson(@RequestBody @NonNull Person person) {
        people.put(person.getId(), personService.validatePerson(person));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(@NonNull ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
