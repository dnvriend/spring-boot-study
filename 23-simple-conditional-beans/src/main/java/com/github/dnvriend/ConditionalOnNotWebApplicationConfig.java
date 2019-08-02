package com.github.dnvriend;

import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Conditional that only matches when the application context is (not) a web application context.
 */
@Configuration
public class ConditionalOnNotWebApplicationConfig {
    @Bean
    @ConditionalOnWebApplication
    public A beanA(){
        return new A(); // will initiate if the context is
        // WebApplicationContext.
    }

    @Bean
    @ConditionalOnNotWebApplication
    public B beanB(){
        return new B(); // will initiate if the context is not
        // WebApplicationContext.
    }
}
