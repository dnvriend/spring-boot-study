package com.github.dnvriend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.ConversionService;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final ConversionService conversionService;

    public Application(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Integer answer_to_life_the_universe_and_everything = conversionService.convert("42", Integer.class);
        System.out.println("The answer to life the universe and everything is: " + answer_to_life_the_universe_and_everything);
    }
}