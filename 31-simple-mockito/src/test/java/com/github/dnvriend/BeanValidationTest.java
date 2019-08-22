package com.github.dnvriend;

import com.github.dnvriend.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BeanValidationTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateUserWithErrors() {
        User user = User.builder().name("   ").age(140).build();
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        assertThat(validate).isNotEmpty();
    }

    @Test
    void validateUserWithNoErrors() {
        User user = User.builder().name("dnvriend").age(42).build();
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        assertThat(validate).isEmpty();
    }
}
