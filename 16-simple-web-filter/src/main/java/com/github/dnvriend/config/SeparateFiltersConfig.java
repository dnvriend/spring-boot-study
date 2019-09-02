package com.github.dnvriend.config;

import com.github.dnvriend.filters.FirstFilter;
import com.github.dnvriend.filters.FourthFilter;
import com.github.dnvriend.filters.SecondFilter;
import com.github.dnvriend.filters.ThirdFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "com.dnvriend.filter", value = "separate", havingValue = "true")
public class SeparateFiltersConfig  {

    @Bean
    public FilterRegistrationBean<FirstFilter> firstFilterRegistrationBean() {
        log.debug("Adding FilterRegistrationBean for FirstFilter");
        FilterRegistrationBean<FirstFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new FirstFilter());
        filterRegistrationBean.setOrder(0);
        filterRegistrationBean.addUrlPatterns("*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<SecondFilter> secondFilterRegistrationBean() {
        log.debug("Adding FilterRegistrationBean for SecondFilter");
        FilterRegistrationBean<SecondFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new SecondFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<ThirdFilter> thirdFilterRegistrationBean() {
        log.debug("Adding FilterRegistrationBean for ThirdFilter");
        FilterRegistrationBean<ThirdFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ThirdFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("*");
        return filterRegistrationBean;
    }


    @Bean
    public FilterRegistrationBean<FourthFilter> fourthFilterRegistrationBean() {
        log.debug("Adding FilterRegistrationBean for FourthFilter");
        FilterRegistrationBean<FourthFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new FourthFilter());
        filterRegistrationBean.setOrder(3);
        filterRegistrationBean.addUrlPatterns("*");
        return filterRegistrationBean;
    }
}
