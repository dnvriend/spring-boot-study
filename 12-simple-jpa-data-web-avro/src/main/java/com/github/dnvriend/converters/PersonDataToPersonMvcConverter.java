package com.github.dnvriend.converter;

import com.github.dnvriend.controller.PersonMvc;
import com.github.dnvriend.repository.PersonData;
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
