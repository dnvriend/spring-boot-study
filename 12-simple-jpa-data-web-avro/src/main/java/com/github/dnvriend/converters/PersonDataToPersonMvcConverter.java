package com.github.dnvriend.converters;

import com.github.dnvriend.controllers.PersonMvc;
import com.github.dnvriend.repository.PersonData;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public class PersonDataToPersonMvcConverter implements Converter<PersonData, PersonMvc> {
    @Override
    public PersonMvc convert(PersonData source) {
        return PersonMvc.builder()
                .id(source.getId())
                .name(Optional.ofNullable(source.getPerson().getName()).map(String::valueOf))
                .age(Optional.ofNullable(source.getPerson().getAge()))
                .build();
    }
}
