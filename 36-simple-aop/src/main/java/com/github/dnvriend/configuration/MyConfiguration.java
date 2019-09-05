package com.github.dnvriend.configuration;

import com.github.dnvriend.aspect.ExampleAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class MyConfiguration {

    @Bean
    public ExampleAspect exampleAspect() {
        System.out.println("Creating aspect");
        return new ExampleAspect();
    }
}
