package com.github.dnvriend.converters;

import com.github.dnvriend.controllers.PersonData;
import com.github.dnvriend.repositories.PersonEntity;
import org.springframework.core.convert.converter.Converter;

public class PersonDataToPersonEntityConverter implements Converter<PersonData, PersonEntity> {

    @Override
    public PersonEntity convert(PersonData personData) {
        return PersonEntity.builder()
            .id(personData.getId())
            .name(personData.getName())
            .age(personData.getAge())
            .build();
    }
}
