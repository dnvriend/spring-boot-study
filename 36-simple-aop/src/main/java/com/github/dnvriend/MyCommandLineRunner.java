package com.github.dnvriend;

import com.github.dnvriend.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    MyService myService;

    @Override
    public void run(String... args) {
        myService.say("Hi!");
    }
}
