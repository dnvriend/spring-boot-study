package com.github.dnvriend;

import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// see: https://www.baeldung.com/spring-boot-console-app
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    // https://www.baeldung.com/spring-value-annotation
    @Value("${my.value:World!}")
    String myValue;

    @Value("${my.week:WEDNESDAY}")
    Week week;

    @Value("${my.number:42}")
    int myNumber;

    @Value("${my.active:false}")
    boolean isActive;

    @Autowired
    MyProperties props;

    @Autowired
    MyBean myBean;

    @Value("${my.names:a,b,c}")
    String[] names;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello: " + myValue);
        System.out.println(myNumber);
        System.out.println(isActive);
        System.out.println("Week: " + week);
        System.out.println(props);
        myBean.say("Hi!");
        Stream.of(names).forEach(System.out::println);
    }
}
