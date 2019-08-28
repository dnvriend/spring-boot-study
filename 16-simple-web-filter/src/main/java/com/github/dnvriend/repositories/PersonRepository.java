package com.github.dnvriend.repositories;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository {

    private Stream<Person> people = Stream.of(
        Person.builder().id(1).name("dnvriend").age(42).build(),
        Person.builder().id(1).name("dnvriend").age(42).build(),
        Person.builder().id(1).name("dnvriend").age(42).build(),
        Person.builder().id(1).name("dnvriend").age(42).build(),
        Person.builder().id(1).name("dnvriend").age(42).build()
    );

    public Stream<Person> getPeople() {
        return people;
    }

    public Optional<Person> getPersonById(int id) {
        return people.filter(p -> p.getId() == id).findFirst();
    }

    public Stream<Person> getPeopleByName(String name) {
        return people.filter(p -> p.getName().equals(name));
    }

    public Stream<Person> getPeopleByAge(int age) {
        return people.filter(p -> p.getAge() == age);
    }
}
