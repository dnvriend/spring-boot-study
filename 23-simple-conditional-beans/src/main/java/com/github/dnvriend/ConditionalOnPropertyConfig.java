package com.github.dnvriend;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Conditional on property checks if the specified property has the specific value. It checks by
 * default if the property is in the Environment and not equals to false.
 */
@Configuration
public class ConditionalOnPropertyConfig {

    @Bean
    @ConditionalOnProperty(name = "test.property", havingValue = "A")
    public A beanA() {
        return new A();
    }

    @Bean
    @ConditionalOnProperty(name = "test.property", havingValue = "B")
    public B beanB() {
        return new B();
    }

    @Bean
    @ConditionalOnProperty(name = "test.property", havingValue = "C", matchIfMissing = true)
    public C beanC() {
        return new C();
    }
}
