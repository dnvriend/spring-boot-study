package com.github.dnvriend.config;

import com.codepoetics.protonpack.StreamUtils;
import javax.servlet.Filter;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
//@ConditionalOnProperty(prefix = "com.dnvriend.filters", value = "separate", havingValue = "false")
@EnableConfigurationProperties(FilterProperties.class)
public class FilterRegistrationConfig implements BeanFactoryAware,
    ApplicationContextAware, ImportBeanDefinitionRegistrar {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("====>>>> Setting BeanFactory");
    }

    private final FilterProperties filterProperties;

    public FilterRegistrationConfig(FilterProperties filterProperties) {
        this.filterProperties = filterProperties;
        System.out.println("==========>>>>> Creating FilterRegConfig");
    }

    @Override
    public void registerBeanDefinitions(
        AnnotationMetadata importingClassMetadata,
        BeanDefinitionRegistry registry) {
        System.out.println("=======>>>> registerBeanDefs");
//        StreamUtils
//            .zipWithIndex(filterProperties.getFilters().stream())
//            .forEach(indexed -> {
//                GenericBeanDefinition filterBeanDefinition = createFilterBeanDefinition(indexed.getValue());
//                String filterBeanName = determineBeanName(indexed.getValue());
//                registry.registerBeanDefinition(filterBeanName, filterBeanDefinition);
//                GenericBeanDefinition filterRegistrationBean =
//                registry.registerBeanDefinition(
//                    String.format("FilterRegistrationBean%s", filterBeanName), indexed.getIndex());
//            });
    }

//    static String determineBeanName(Class<? extends Filter> beanClass) {
//        return beanClass.getName().toLowerCase();
//    }
//
//    static GenericBeanDefinition createFilterBeanDefinition(
//        Class<? extends Filter> filterClass) {
//        GenericBeanDefinition filterClassBeanDefinition = new GenericBeanDefinition();
//        filterClassBeanDefinition.setBeanClass(filterClass);
//        return filterClassBeanDefinition;
//    }
//
//    static GenericBeanDefinition createFilterRegistrationBean(
//        ,
//        long order) {
//        GenericBeanDefinition filterRegistrationBeanDefinition = new GenericBeanDefinition();
//        MutablePropertyValues mpv = filterRegistrationBeanDefinition.getPropertyValues();
//        filterRegistrationBeanDefinition.setBeanClass(FilterRegistrationBean.class);
//        mpv.add("order", order);
//        mpv.add("filter", )
//        return filterRegistrationBeanDefinition;
//    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("=====>>> setApplicationContext");
    }

}
