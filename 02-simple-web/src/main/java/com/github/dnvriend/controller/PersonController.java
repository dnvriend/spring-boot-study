package com.github.dnvriend.controller;

import com.github.dnvriend.model.Person;
import com.github.dnvriend.status.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Api(value = "Person resource", description = "operations related to Person")
@RestController
@RequestMapping("/person")
public class PersonController {

    private Map<Integer, Person> people;

    public PersonController() {
        this.people = new ConcurrentHashMap<>();
        this.people.put(1, Person.builder().id(1).name("dnvriend").age(42).build());
    }

    @ApiOperation(value = "Get a Person")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Person Found"),
            @ApiResponse(code = 404, message = "Person Not Found")
    })
    @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Person getPerson(@PathVariable("userId") int userId) {
        return Optional
                .ofNullable(people.get(userId))
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("User with id %s", userId)));
    }

    @ApiOperation(value = "Put a Person")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Person stored")
    })
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putPerson(@RequestBody @NonNull Person person) {
        people.put(person.getId(), person);
    }

    @ApiOperation(value = "Get list of person")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of person")
    })
    @GetMapping(path = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Stream<Person> getPeopleList() {
        return people.values().stream();
    }

    @ApiOperation(value = "Get list of person")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of person")
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public Stream<Person> getPeople() {
        return getPeopleList();
    }
}
