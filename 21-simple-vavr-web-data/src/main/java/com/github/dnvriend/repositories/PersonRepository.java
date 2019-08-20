package com.github.dnvriend.repositories;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    Option<PersonEntity> findPersonById(Long id);

    Seq<PersonEntity> findByName(String name);

    Seq<PersonEntity> findByAge(Integer age);
}
