package com.github.dnvriend.repositories;

import com.github.dnvriend.controllers.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
