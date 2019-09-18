package com.github.dnvriend.converter;

import com.github.dnvriend.controller.PersonData;
import com.github.dnvriend.repository.PersonEntity;
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
