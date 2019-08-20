package com.github.dnvriend;

import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Conditional that checks the Java version
 */
@Configuration
public class ConditionalOnJavaConfig {

    @Bean
    @ConditionalOnJava(value = JavaVersion.EIGHT)
    public A beanA() {
        return new A();
    }

    @Bean
    @ConditionalOnJava(value = JavaVersion.EIGHT, range = ConditionalOnJava.Range.OLDER_THAN)
    public B beanB() {
        return new B();
    }
}
