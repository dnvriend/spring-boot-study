# spring-boot-study

A small study project on spring-boot with Java

## HTTP

```bash
# get
$ http :8080/k1/k2/k3/2019-01-01T09:00:00.000Z/2019-01-01T13:00:00.000Z
```

## Test Annotations

- @ActiveProfiles: enable a profile eg. for testing on a @SpringBootTest
- @Profile: state that the @Configuration is active for the given @Profile

## Spring Bean
A spring bean is a Java object that is managed by the Spring container.

## Bean Factory
The `BeanFactory` is the root interface for accessing the Spring container. It provides the `getBean()` interface that provides beans that will be created and managed by the container.

## ApplicationContext
The `ApplicationContext` is the central interface within a Spring application that is used for providing configuration information to the application.

It implements the BeanFactory interface. Hence, the ApplicationContext includes all functionality of the BeanFactory and much more! Its main function is to support the creation of big business applications.

Features:

- Bean instantiation/wiring
- Automatic BeanPostProcessor registration
- Automatic BeanFactoryPostProcessor registration
- Convenient MessageSource access (for i18n)
- ApplicationEvent publication

Most spring applications use the following ApplicationContext:

- `GenericApplicationContext`
  - `GenericWebApplicationContext`
    - `ServletWebServerApplicationContext`
      - `AnnotationConfigServletWebServerApplicationContext` 

## BeanFactory vs ApplicationContext
As the ApplicationContext provides all the functionality that the BeanFactory also provides, you would use the ApplicationContext.

## Register beans
There are several ways to register beans with the bean factory. A common way is to use an XML or annotation based (@Configuration/@Bean) definition. It is also possible to provide bean definitions using the `BeanDefinitionRegistry`. There are a lot of types that implement this interface such as the `SimpleBeanDefinitionRegistry`, `GenericApplicationContext` and so on. 

You register a bean with the BeanDefinitionRegistry with a `BeanDefinition`. The BeanDefinition describes a bean instance using setter methods to specify the characteristics of the bean. 

These beans are not managed by the spring container. 

## Resources
- [spring framework documentation](https://docs.spring.io/spring/docs/current/spring-framework-reference/)
- [spring-boot-reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [spring-framework](https://spring.io/projects/spring-framework)
- [Maven - multi-module project](https://www.baeldung.com/maven-multi-module)
- [JUnit5](https://www.baeldung.com/junit-5)
- [Assertj](https://joel-costigliola.github.io/assertj/)
- [Jackson Improvements in Spring](https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring)
- [Google Style Guide](https://github.com/google/styleguide)
