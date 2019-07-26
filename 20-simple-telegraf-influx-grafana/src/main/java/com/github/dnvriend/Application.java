package com.github.dnvriend;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
class Person {
    private final int id;
    private final String name;
    private final int age;
}

@RestController
@RequestMapping("/person")
class PersonController {
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
        people.put(person.getId(), person);
    }
}

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(@NonNull String msg) {
        super(msg);
    }
}

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
