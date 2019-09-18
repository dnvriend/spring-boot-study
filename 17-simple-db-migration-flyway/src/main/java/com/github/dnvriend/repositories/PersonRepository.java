package com.github.dnvriend.repository;

import com.github.dnvriend.controller.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
