package com.github.dnvriend.configurations;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MyConfiguration  {

    private final ApplicationContext applicationContext;

    public MyConfiguration(ApplicationContext applicationContext) {
        System.out.println(" ====>>>>>> Injecting appContext");
        this.applicationContext = applicationContext;
    }

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor(final Environment environment) {
        System.out.println(" ============>>>>>> BeanFactoryPP called");
        return beanFactory -> {
            environment.getProperty("home");
        };
    }
}
