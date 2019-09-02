package com.github.dnvriend.config;

import com.github.dnvriend.filters.WrapFilter;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.context.ServletContextAware;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "com.dnvriend.filter", value = "wrapped", havingValue = "true")
@EnableConfigurationProperties(FilterProperties.class)
public class WrappedFiltersConfig implements ServletContextAware {

    private final FilterProperties filterProperties;
    private final GenericApplicationContext context;

    public WrappedFiltersConfig(FilterProperties filterProperties,
        GenericApplicationContext context) {
        this.filterProperties = filterProperties;
        this.context = context;
        System.out.println("Context: " + context.getClass().getName());
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
//        servletContext.addFilter()
    }

//        @Bean
//    public FilterRegistrationBean<WrapFilter> wrapFilterFilterRegistrationBean() {
//        log.debug("Adding Wrapped filter");
//        filterProperties.getFilters()
//            .forEach(filterClass -> context.registerBeanDefinition(
//                determineBeanName(filterClass),
//                createFilterBeanDefinition(filterClass)));
//        List<Filter> filters = filterProperties.getFilters().stream()
//            .map(WrappedFiltersConfig::determineBeanName)
//            .map(context::getBean)
//            .map(obj -> (Filter) obj)
//            .collect(Collectors.toList());
//        FilterRegistrationBean<WrapFilter> filterRegistrationBean = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new WrapFilter(filters));
//        filterRegistrationBean.setOrder(0);
//        filterRegistrationBean.addUrlPatterns("*");
//        return filterRegistrationBean;
//    }

    @Bean
    public FilterRegistrationBean wrapFilterFilterRegistrationBean() {
        log.debug("Adding Wrapped filter");
        filterProperties.getFilters()
            .forEach(filterClass -> context.registerBeanDefinition(
                determineBeanName(filterClass),
                createFilterBeanDefinition(filterClass)));

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WrapFilter(Collections.emptyList()));
        filterRegistrationBean.setOrder(0);
        filterRegistrationBean.addUrlPatterns("*");
        return filterRegistrationBean;
    }

//    @Bean
//    public File wrapFilterFilterRegistrationBean() {
//        log.debug("Adding Wrapped filter");
//        filterProperties.getFilters()
//            .forEach(filterClass -> context.registerBeanDefinition(
//                determineBeanName(filterClass),
//                createFilterBeanDefinition(filterClass)));
//
////        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
////        filterRegistrationBean.setFilter(new WrapFilter(Collections.emptyList()));
////        filterRegistrationBean.setOrder(0);
////        filterRegistrationBean.addUrlPatterns("*");
//        return new File("/tmp");
//    }

//    @Bean
//    public Filter wrapFilterFilterRegistrationBean() {
//        log.debug("Adding Wrapped filter");
//        filterProperties.getFilters()
//            .forEach(filterClass -> context.registerBeanDefinition(
//                determineBeanName(filterClass),
//                createFilterBeanDefinition(filterClass)));
//        return new WrapFilter(Collections.emptyList());
//    }


    private static String determineBeanName(Class<? extends Filter> beanClass) {
        return beanClass.getSimpleName();
    }

    private static GenericBeanDefinition createFilterBeanDefinition(
        Class<? extends Filter> filterClass) {
        GenericBeanDefinition filterClassBeanDefinition = new GenericBeanDefinition();
        filterClassBeanDefinition.setBeanClass(filterClass);
        return filterClassBeanDefinition;
    }

}
