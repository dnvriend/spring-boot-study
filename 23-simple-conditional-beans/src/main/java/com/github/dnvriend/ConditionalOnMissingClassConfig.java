package com.github.dnvriend;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Conditional that only matches when the specified Class is missing from the classpath.
 */
@Configuration
public class ConditionalOnMissingClassConfig {

    @Bean
    @ConditionalOnMissingClass(value = {"com.sample.Dummy"})
    public A beanA() {
        return new A(); // will get created as Dummy class is missing
        // from classpath.
    }

    @Bean
    @ConditionalOnMissingClass(value = {"com.sample.bean.conditional.model.C"})
    public B beanB() {
        return new B(); // won't be created as C is present in classpath.
    }

}