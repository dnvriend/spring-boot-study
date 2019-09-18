package com.github.dnvriend.repository;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository {

    public Stream<Person> getPeople() {
        return Stream.of(
            Person.builder().id(1).name("dnvriend").age(40).build(),
            Person.builder().id(2).name("dnvriend").age(41).build(),
            Person.builder().id(3).name("dnvriend").age(42).build(),
            Person.builder().id(4).name("dnvriend").age(43).build(),
            Person.builder().id(5).name("dnvriend").age(44).build()
        );
    }

    public Optional<Person> getPersonById(int id) {
        return getPeople().filter(p -> p.getId() == id).findFirst();
    }

    public Stream<Person> getPeopleByName(String name) {
        return getPeople().filter(p -> p.getName().equals(name));
    }

    public Stream<Person> getPeopleByAge(int age) {
        return getPeople().filter(p -> p.getAge() == age);
    }
}
