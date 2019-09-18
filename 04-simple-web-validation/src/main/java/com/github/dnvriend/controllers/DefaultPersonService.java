package com.github.dnvriend.controller;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class DefaultPersonService implements PersonService {

    private final Validator validator;

    public DefaultPersonService(@NonNull Validator validator) {
        this.validator = validator;
    }

    @Override
    public Person validatePerson(@NonNull Person person) {
        val violations = validator.validate(person);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return person;
    }
}
