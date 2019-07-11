package com.github.dnvriend;

import com.github.dnvriend.controllers.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class Application {

    @Bean
    public Map<Integer, Person> getPeople() {
        return new ConcurrentHashMap<>();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
