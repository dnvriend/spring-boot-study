package com.github.dnvriend;

import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Conditional that only matches when the specified resource is available on the classpath.
 */
@Configuration
public class ConditionalOnResourceConfig {
    @Bean
    @ConditionalOnResource(resources={"classpath:application.properties"})
    public A beanA(){
        return new A(); // will initiate as application.properties is in
        // classpath.
    }

    @Bean
    @ConditionalOnResource(resources={"file:///e:/doc/data.txt"})
    public B beanB(){
        return new B(); // will not initialize as the file is not
        // present in the given location.
    }
}
