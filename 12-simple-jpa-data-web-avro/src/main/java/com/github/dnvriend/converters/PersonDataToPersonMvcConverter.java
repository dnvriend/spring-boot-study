package com.github.dnvriend.converters;

import com.github.dnvriend.controllers.PersonMvc;
import com.github.dnvriend.repositories.PersonData;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;

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
