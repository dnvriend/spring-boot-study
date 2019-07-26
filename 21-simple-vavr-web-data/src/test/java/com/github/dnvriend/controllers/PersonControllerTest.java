package com.github.dnvriend.controllers;

import com.github.dnvriend.repositories.PersonEntity;
import com.github.dnvriend.repositories.PersonEntityAssert;
import com.github.dnvriend.repositories.PersonRepository;
import io.vavr.collection.List;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class PersonControllerTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    @DisplayName("Given: new person, when: saved to repo, then: repo contains person")
    void given_newPerson_when_savedToRepository_then_repositoryContainsPerson() {
        // given: empty repo
        assertThat(personRepository.count()).isEqualTo(0);

        // and: new person
        val person = PersonEntity.builder().name("dnvriend").age(42).build();

        // when: saved to repo
        val saved = personRepository.save(person);

        // then: repo contains person
        val actual = personRepository.findPersonById(saved.getId()).get();

        PersonEntityAssert.assertThat(actual)
                .hasNoNullFieldsOrProperties()
                .hasId(1L)
                .hasName("dnvriend")
                .hasAge(42);
    }

    @Test
    @DisplayName("Given: seq of person, when: find by name: then: return seq of person")
    void given_listOfPerson_when_findByName_then_returnSeqOfPerson() {
        // given: empty repo
        assertThat(personRepository.count()).isEqualTo(0);

        // and: list of person
        val xs = List.of(
            PersonEntity.builder().name("dnvriend").age(42).build(),
            PersonEntity.builder().name("dnvriend").age(43).build(),
            PersonEntity.builder().name("dnvriend").age(44).build(),
            PersonEntity.builder().name("dnvriend").age(45).build()
        );
        // when save all to repository
        personRepository.saveAll(xs);

        // when: find by name
        val actual = personRepository.findByName("dnvriend");

        // then:
        assertThat(actual).isEqualTo(List.of(
                PersonEntity.builder().id(1L).name("dnvriend").age(42).build(),
                PersonEntity.builder().id(2L).name("dnvriend").age(43).build(),
                PersonEntity.builder().id(3L).name("dnvriend").age(44).build(),
                PersonEntity.builder().id(4L).name("dnvriend").age(45).build()
        ));
    }
}
