package com.github.dnvriend.services;

import com.github.dnvriend.domain.Person;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    public Mono<Person> findPersonById(long id) {
        return Mono.just(Person.builder()
            .id(id)
            .name(String.format("name - %s", id))
            .age(42)
            .build());
    }
}
