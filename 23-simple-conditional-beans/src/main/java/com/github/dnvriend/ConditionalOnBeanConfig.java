package com.github.dnvriend;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class A {

}

class B {

}

class C {

}

class SimpleInt {

}

/**
 * Conditional that only matches when the given Bean name or classes are already present in the
 * BeanFactory.
 */
@Configuration
public class ConditionalOnBeanConfig {

    @Bean
    public A beanA() {
        return new A();
    }

    @Bean
    @ConditionalOnBean(name = "beanA")
    public B beanB() {
        return new B(); // it will initialize as beanA is present in the beanFactory.
    }

    @Bean
    @ConditionalOnBean
    public C beanC() {
        return new C(); // will not get initialized as there is no bean with return type C in BeanFactory.
    }

    @Bean
    public SimpleInt beanAInt() {
        return new SimpleInt();
    }

    @Bean
    @ConditionalOnBean
    public SimpleInt beanBInt() {
        return new SimpleInt();
    }
}