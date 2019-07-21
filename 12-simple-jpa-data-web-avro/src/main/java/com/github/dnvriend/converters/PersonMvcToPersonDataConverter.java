package com.github.dnvriend.converters;

import com.github.dnvriend.avro.Person;
import com.github.dnvriend.controllers.PersonMvc;
import com.github.dnvriend.repository.PersonData;
import org.springframework.core.convert.converter.Converter;

public class PersonMvcToPersonDataConverter implements Converter<PersonMvc, PersonData> {
    @Override
    public PersonData convert(PersonMvc source) {
        return PersonData
                .builder()
                .person(Person
                        .newBuilder()
                        .setName(source.getName().orElse(null))
                        .setAge(source.getAge().orElse(null))
                        .build()
                ).build();
    }
}
