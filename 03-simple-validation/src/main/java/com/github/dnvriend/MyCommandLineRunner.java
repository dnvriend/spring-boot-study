package com.github.dnvriend;

import javax.validation.Validation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


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
