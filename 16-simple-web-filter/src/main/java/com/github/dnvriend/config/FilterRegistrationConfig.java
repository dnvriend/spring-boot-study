package com.github.dnvriend.config;

import com.github.dnvriend.filters.FirstFilter;
import com.github.dnvriend.filters.SecondFilter;
import com.github.dnvriend.filters.ThirdFilter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

/**
 * Does not work, because @Bean FilterRegistrationBean and @Bean Filters are picked up via
 * classpath scanning, indexed and initialized and added to the ServletContextInitializerBean
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "com.dnvriend.filter", value = "config", havingValue = "true")
@EnableConfigurationProperties(FilterProperties.class)
public class FilterRegistrationConfig implements BeanFactoryPostProcessor  {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
        throws BeansException {
        List<Class <? extends Filter>> filters = Arrays.asList(
            FirstFilter.class,
            ThirdFilter.class,
            SecondFilter.class
        );
        initializeFilters(filters, (DefaultListableBeanFactory) beanFactory);
    }

    public FilterRegistrationConfig() {
        log.debug("Created FilterRegistrationConfig via default constructor");
    }

    public void initializeFilters(List<Class<? extends Filter>> filters, DefaultListableBeanFactory beanFactory) {
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
        return String.format("%s%s", registrationBeanName, filterBeanName);
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
