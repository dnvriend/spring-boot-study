package com.github.dnvriend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonTest {

    Person person;

    @BeforeEach
    void setUp() {
        person = Person.builder()
                .name("dnvriend")
                .age(42)
                .build();
    }

    @Test
    void given_personWithAllFields_then_assertThatPersonHasNoNullFieldsOrProperties() {
        PersonAssert.assertThat(Person.builder()
                .name("dnvriend")
                .age(42)
                .build())
                .hasNoNullFieldsOrProperties()
                .hasName("dnvriend")
                .hasAge(42);
    }

    @Test
    void given_personWithNullAgeField_then_assertThatPersonHasNameFieldAndAgeFieldNull() {
        PersonAssert.assertThat(
                Person.builder()
                .name("dnvriend")
                .build())
                .hasNoNullFieldsOrPropertiesExcept("age")
                .hasName("dnvriend");
    }

    @Test
    void given_personWithNullNameAndAgeField_then_assertThatPersonHasNullFields() {
        PersonAssert.assertThat(
                Person.builder()
                .build())
                .hasNoNullFieldsOrPropertiesExcept("name", "age");
    }

}
