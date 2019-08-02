# 23-simple-conditional-beans
Spring supports creating beans on specific conditions. For a lot of standard conditions, you don't have to
add some if-else logic in your `@Bean` method to provide the correct bean, depending on some condition.

Spring supports the following conditional annotations:

- [Class Conditions](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html#boot-features-class-conditions): let `@Configuration` classes be included based on the presence or absence of specific classes. The following annotations are available:
  - ConditionalOnClass:  
  - ConditionalOnMissingClass:
- [Bean Conditions](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html#boot-features-bean-conditions): let a `@Bean` be included on the presence or absence of specific beans. You can use the `value` attribute to specify beans by type or `name` to specify beans by name. The `search` attribute lets you limit the ApplicationContext hierarchy that should be considered when searching for beans. These annotations should be used on auto-configuration classes, because these are guaranteed to be loaded after any user-defined bean definitions have been added 
  - ConditionalOn(Missing)Bean: 
  - ConditionalOnJava:
  - ConditionalOnProperty:
  - ConditionalOnResource:
  - ConditionalOn(Not)WebApplication:
  - ConditionalOn(Missing)Class:
   
- [Property Conditions](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html#boot-features-property-conditions): lets `@Configuration` be included based on a Spring Environment property. Use the `prefix` and `name` attributes to specify the property that should be checked. By default, any property that exists and is not equal to false is matched. You can also create more advanced checks by using the `havingValue` and `matchIfMissing` attributes. 
  - ConditionalOnProperty:   
- [Resource Conditions](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html#boot-features-resource-conditions): lets `@Configuration` be included only when a specific resource is present. Resources can be specified by using the usual Spring conventions, as shown in the following example: `file:/home/user/test.dat`.
  - @ConditionalOnResource
- [Web Application Conditions](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html#boot-features-web-application-conditions): let `@Configuration` be included depending on whether the application is a “web application”. A web application is any application that uses a Spring `WebApplicationContext`, defines a session scope, or has a StandardServletEnvironment.
  - ConditionalOnWebApplication:
  - ConditionalOnNotWebApplication:
- [SpEL Expression Condition](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html#boot-features-spel-conditions): lets `@Configuration` be included based on the result of a SpEL expression.
  - ConditionalOnExpression:

## Auto-configured beans
Auto-configuration beans are implemented with standard `@Configuration` classes. Additional `@Conditional` annotations are used to constrain when auto-configuration should apply. Usually, auto-config classes use `@ConditionalOnClass` and `@ConditionalOnMissingBean` annotations. This ensures that auto-configuration applies only when relevant classes are found and when you have not declared your own `@Configuration`.

## Locating auto-configuration candidates
Spring Boot checks for the presence of a `META-INF/spring.factories` file within your published jar. The file should list your configuration classes under the `EnableAutoConfiguration` key, as shown in the following example:

```text
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.mycorp.libx.autoconfigure.LibXAutoConfiguration,\
com.mycorp.libx.autoconfigure.LibXWebAutoConfiguration
```

## Resources
- [Conditional Annotations](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html#boot-features-condition-annotations)
- [Conditional beans creation in spring boot](https://iamninad.com/conditional-bean-creation-in-spring-boot/)