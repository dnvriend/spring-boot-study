package com.github.dnvriend;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Conditional that only matches when the specified bean is missing from the beanfactory.
 */
@Configuration
public class ConditionalOnMissingBeanConfig {

    @Bean
    public A beanA(){
        return new A(); // will initialize as normal
    }

    @Bean
    @ConditionalOnMissingBean(name="beanA")
    public B beanB(){
        return new B(); // it will not initialize as
        // beanA is present in the beanFactory.
    }

    @Bean
    @ConditionalOnMissingBean(name="beanD")
    public C beanC(){
        return new C(); // will get initialized as there is no
        // bean with name beanD in BeanFactory.
    }

}