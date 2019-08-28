# 16-simple-web-filter
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
