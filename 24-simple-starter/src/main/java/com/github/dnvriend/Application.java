package com.github.dnvriend;

import com.dnvriend.starter.Greeter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final Greeter greeter;

    public Application(Greeter greeter) {
        this.greeter = greeter;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(greeter.greet());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
