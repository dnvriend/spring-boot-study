# 16-simple-web-filter

## Introduction
A Filter can be registered using either FilterRegistrationBean class or @Component annotation or @ServletComponentScan annotation. FilterRegistrationBean registers a Filter as Spring bean and it provides methods to add URL mappings, set Filter order etc. When we register a Filter using Spring @Component, we can set Filter order using Spring @Order annotation but there is no way to change default URL mappings in this case. When we register Filter using @ServletComponentScan, our filters must be annotated with @WebFilter annotation and we can add URL mappings using its urlPatterns attribute but we cannot set Filter order in this case. @ServletComponentScan works only when using embedded server. Here on this page we will provide complete Spring Boot Filter example with filters, servlets and Spring controller.

## Creating a filter
To create a filter, follow these steps:

- Create a class that implements `javax.serlvet.Filter`

## Spring WebMvcFilter Configuration
Filters are configured with `WebMvcAutoConfiguration`. 

If you register a @Bean of type javax.servlet.Filter, it applies to all requests. If you also register a @Bean of type FilterRegistrationBean that registers the filter type, then if applies to requests of the urlPattern, and order that you specify.

## Filter ordering
@Order can be any number between Integer.MIN_VALUE and Integer.MAX_VALUE as defined in Ordered interface. Integer.MAX_VALUE basically means that it will have THE LOWEST PRIORITY and MAX_VALUE -1 has one higher priority than previous one. HIGHER value has LOWER precedence.

- Filters that wrap ServletRequest should be ordered less than <= 0.
- OrderedFormContentFilter has order -9900.

Alternatively, spring-security has the FilterChainProxy that manages a specific list of filters that must be applied in a specified order.

When we register filters using FilterRegistrationBean, we can set filter order using its setOrder() method.

```
filterRegBean.setOrder(Ordered.LOWEST_PRECEDENCE); 
```

- Ordered.HIGHEST_PRECEDENCE: This is the highest precedence.
- Ordered.LOWEST_PRECEDENCE: This is the lowest precedence. 

Lower the order number, higher the precedence. Find the sample precedence order. 

```
Ordered.LOWEST_PRECEDENCE -2 > Ordered.LOWEST_PRECEDENCE -1;
Ordered.HIGHEST_PRECEDENCE +1 > Ordered.HIGHEST_PRECEDENCE +2;
```

It is usually safe to leave the filter beans unordered. Spring boot provides them default order and that is usually Ordered.LOWEST_PRECEDENCE. If we want to run our custom filters before or after any in-built filter such as Spring security filter, we need to order them using FilterRegistrationBean. It means if we want to run our custom filter after Spring security filter, we need to create our own FilterRegistrationBean for Spring security filter and specify the order.

## Bean Ordering
Your target beans can implement the org.springframework.core.Ordered interface or use the @Order or standard @Priority annotation if you want items in the array or list to be sorted in a specific order. Otherwise, their order follows the registration order of the corresponding target bean definitions in the container.

You can declare the @Order annotation at the target class level and on @Bean methods, potentially by individual bean definition (in case of multiple definitions that use the same bean class). @Order values may influence priorities at injection points, but be aware that they do not influence singleton startup order, which is an orthogonal concern determined by dependency relationships and @DependsOn declarations.

Note that the standard javax.annotation.Priority annotation is not available at the @Bean level, since it cannot be declared on methods. Its semantics can be modeled through @Order values in combination with @Primary on a single bean for each type.


## Spring provided filters

- FormContentFilter (-9900)
- OrderedHiddenHttpMethodFilter (-10000) 

## FilterRegistrationBean
A `FilterRegistrationBean`

```
@Bean
public Filter shallowEtagHeaderFilter() {
  return new ShallowEtagHeaderFilter();
}
@Bean
public FilterRegistrationBean shallowEtagHeaderFilterRegistration() {
  FilterRegistrationBean result = new FilterRegistrationBean();
  result.setFilter(this.shallowEtagHeaderFilter());
  result.addUrlPatterns("/user/*");
  result.setName("shallowEtagHeaderFilter");
  result.setOrder(1);
  return result;
}
```

## Resources
- [spring-web - Filters](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#filters)
- [How to Define a Spring Boot Filter?](https://www.baeldung.com/spring-boot-add-filter)
- [A Custom Filter in the Spring Security Filter Chain](https://www.baeldung.com/spring-security-custom-filter)
- [Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- [Find the registered spring security filters](https://www.baeldung.com/spring-security-registered-filters)
- [How to Return 404 with Spring WebFlux](https://www.baeldung.com/spring-webflux-404)
- [Introduction to Spring Reactor](https://www.baeldung.com/spring-reactor)
- [About Spring Boot Filter](https://www.concretepage.com/spring-boot/spring-boot-filter)
- [Spring Context Events](https://www.baeldung.com/spring-context-events)
- [Bean Life Cycle](https://www.journaldev.com/2637/spring-bean-life-cycle)
- [ServletComponentBeans](https://www.logicbig.com/tutorials/spring-framework/spring-boot/servlet-component-beans.html)

