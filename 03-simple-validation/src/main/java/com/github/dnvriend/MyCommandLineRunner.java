package com.github.dnvriend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.stream.Stream;
import lombok.val;


@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    MyProperties props;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(props);

        val factory = Validation.buildDefaultValidatorFactory();
        val validator = factory.getValidator();
        val violations = validator.validate(props);
        violations.forEach(violation -> System.out.println(violation.getMessage()));
    }
}
