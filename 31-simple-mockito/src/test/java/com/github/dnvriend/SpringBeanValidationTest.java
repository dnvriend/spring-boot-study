package com.github.dnvriend;

import com.github.dnvriend.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SpringBeanValidationTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateUserWithErrors() {
        BeanWrapper wrapper = new BeanWrapperImpl(User.class);
        wrapper.setPropertyValue("name", "   ");
        wrapper.setPropertyValue("age", 42);
        User user = (User) wrapper.getWrappedInstance();
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        assertThat(validate).isNotEmpty();
    }

    @Test
    void validateUserWithNoErrors() {
        BeanWrapper wrapper = new BeanWrapperImpl(User.class);
        wrapper.setPropertyValue("name", "dnvriend");
        wrapper.setPropertyValue("age", 42);
        User user = (User) wrapper.getWrappedInstance();
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        assertThat(validate).isEmpty();
    }
}
