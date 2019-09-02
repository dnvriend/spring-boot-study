package com.github.dnvriend.config;

import java.util.List;
import javax.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "com.dnvriend.filter", value = "config", havingValue = "true")
public class FilterRegistrationConfig {

    @Bean
//    @ConditionalOnProperty(prefix = "com.dnvriend.filter", value = "config", havingValue = "true")
    public static BeanFactoryPostProcessor beanFactoryPostProcessor(final Environment environment) {
        return beanFactory -> {
            BindResult<FilterProperties> bindResult = Binder.get(environment)
                .bind("com.dnvriend.filter", FilterProperties.class);
            FilterProperties filterProperties = bindResult.get();
            if(beanFactory instanceof DefaultListableBeanFactory) {
                DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
                initializeFilters(filterProperties.getFilters(), defaultListableBeanFactory);
            }
        };
    }

    private static void initializeFilters(List<Class<? extends Filter>> filters, DefaultListableBeanFactory beanFactory) {
        filters.forEach(filterClass -> {
            String filterBeanName = determineBeanName(filterClass);
            log.debug("Register filter: {} with name: {}", filterClass.getName(), filterBeanName);
            beanFactory.registerBeanDefinition(
                filterBeanName,
                createFilterBeanDefinition(filterClass)
            );
        });

        for (int order = 0; order < filters.size(); order++) {
            Class<? extends Filter> filterClass = filters.get(order);
            String beanName = determineFilterRegistrationName(filterClass);
            log.debug("Registering FilterRegistrationBean with name: {} and order {}", beanName, order);
            Object filterInstance = beanFactory.getBean(filterClass);
            beanFactory.registerBeanDefinition(
                beanName,
                createFilterRegistrationBean(filterInstance, order)
            );
        }
    }

    private static String determineBeanName(Class<? extends Filter> beanClass) {
        return beanClass.getSimpleName();
    }

    private static String determineFilterRegistrationName(Class<? extends Filter> filterClass) {
        String registrationBeanName = "FilterRegistrationBean";
        String filterBeanName = filterClass.getSimpleName();
        String filterRegistrationName = String.format("%s%s", registrationBeanName, filterBeanName);
        return filterRegistrationName;
    }

    private static GenericBeanDefinition createFilterBeanDefinition(
        Class<? extends Filter> filterClass) {
        GenericBeanDefinition filterClassBeanDefinition = new GenericBeanDefinition();
        filterClassBeanDefinition.setBeanClass(filterClass);
        return filterClassBeanDefinition;
    }

    private static GenericBeanDefinition createFilterRegistrationBean(Object filterInstance,
        int order) {
        GenericBeanDefinition filterRegistrationBeanDefinition = new GenericBeanDefinition();
        MutablePropertyValues mpv = filterRegistrationBeanDefinition.getPropertyValues();
        filterRegistrationBeanDefinition.setBeanClass(FilterRegistrationBean.class);
        mpv.add("filter", filterInstance);
        mpv.add("order", order);
        return filterRegistrationBeanDefinition;
    }
}
