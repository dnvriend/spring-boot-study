package com.github.dnvriend.converters;

import com.github.dnvriend.controllers.PersonData;
import com.github.dnvriend.repositories.PersonEntity;
import org.springframework.core.convert.converter.Converter;

public class PersonEntityToPersonDataConverter implements Converter<PersonEntity, PersonData> {
    @Override
    public PersonData convert(PersonEntity personEntity) {
        return PersonData.builder()
                .id(personEntity.getId())
                .name(personEntity.getName())
                .age(personEntity.getAge())
                .build();
    }
}
