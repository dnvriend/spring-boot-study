//package com.github.dnvriend.config;
//
//import javax.servlet.Filter;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.type.AnnotationMetadata;
//
//@Configuration
//@ConditionalOnProperty(prefix = "com.dnvriend.filters", value = "separate", havingValue = "true")
//@EnableConfigurationProperties(FilterProperties.class)
//public class SeparateFiltersConfig implements ImportBeanDefinitionRegistrar {
//
//    private final FilterProperties filterProperties;
//
//    public SeparateFiltersConfig(FilterProperties filterProperties) {
//        this.filterProperties = filterProperties;
//    }
//
//    @Override
//    public void registerBeanDefinitions(
//        AnnotationMetadata importingClassMetadata,
//        BeanDefinitionRegistry registry) {
//        for (Class<? extends Filter> filterClass : filterProperties.getFilters()) {
//            registry.registerBeanDefinition(
//                determineBeanName(filterClass),
//                createBeanDefinition(filterClass)
//            );
//        }
//    }
//
//    static String determineBeanName(Class<?> beanClass) {
//        return beanClass.getName().toLowerCase();
//    }
//
//    static GenericBeanDefinition createBeanDefinition(Class<?> beanClass) {
//        GenericBeanDefinition filterClassBeanDefinition = new GenericBeanDefinition();
//        filterClassBeanDefinition.setBeanClass(beanClass);
//        return filterClassBeanDefinition;
//    }
//}
