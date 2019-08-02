package com.github.dnvriend;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Conditional that only matches when the specified class is available on the classpath.
 */
@Configuration
public class ConditionalOnClassConfig {

    @Bean
    @ConditionalOnClass(value = {java.util.HashMap.class})
    public A beanA() {
        return new A(); // will get created as HashMap class is on the classpath
    }

    @Bean
    @ConditionalOnClass(name = "com.sample.Dummy")
    public B beanB() {
        return new B(); // won't be created as Dummy class is not on classpath
    }

    @Bean
    @ConditionalOnClass(value = SimpleInt.class)
    public C beanC() {
        return new C(); // ASimpleInt is on the classpath in the project.
        //So, C's instance will be created.
    }

}
