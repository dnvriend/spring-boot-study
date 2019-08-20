package com.github.dnvriend;

import com.github.dnvriend.controllers.Person;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Map<Integer, Person> getPeople() {
        return new ConcurrentHashMap<>();
    }
}
