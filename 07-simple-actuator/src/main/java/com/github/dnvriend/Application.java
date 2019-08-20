package com.github.dnvriend;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
            .orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with id %s", userId)));
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

/**
 * Calling 'http :8080/actuator/health' returns: { "myCustom": { "details": { "app": "Alive and
 * Kicking", "error": "Nothing! I'm good." }, "status": "UP" } }, "status": "UP" }
 */
@Component
class MyCustomHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // Use the builder to build the health status details that should be reported.
        // If you throw an exception, the status will be DOWN with the exception message.
        builder.up()
            .withDetail("app", "Alive and Kicking")
            .withDetail("error", "Nothing! I'm good.");
    }
}

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
