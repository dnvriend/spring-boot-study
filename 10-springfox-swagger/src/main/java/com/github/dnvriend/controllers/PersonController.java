package com.github.dnvriend.controllers;

import com.github.dnvriend.status.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Person resource", description = "operations related to Person")
@RestController
@RequestMapping("/person")
public class PersonController {

    Map<Integer, Person> people = new ConcurrentHashMap<>();

    @ApiOperation(value = "Get a Person")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Person Found"),
        @ApiResponse(code = 404, message = "Person Not Found")
    })
    @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Person getPerson(
        @ApiParam(name = "userId", required = true, type = "int", example = "1") @PathVariable("userId") int userId) {
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
        people.put(person.id, person);
    }
}
