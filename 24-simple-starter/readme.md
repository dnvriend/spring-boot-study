# 24-simple-starter

## Note
To avoid the configuration to be scanned and configured by Spring, the package of the starter should be different
than your application.

## Not found message
When the starter is not working, you get the following error:

```test
***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 0 of constructor in com.github.dnvriend.Application required a bean of type 'com.dnvriend.starter.Greeter' that could not be found.

Action:

Consider defining a bean of type 'com.dnvriend.starter.Greeter' in your configuration.
```

## Resources
- [Spring Boot Manual - Creating Your Own Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html)
- [Creating a Custom Starter with Spring Boot](https://www.baeldung.com/spring-boot-custom-starter)
- [Custom starter github](https://github.com/eugenp/tutorials/tree/master/spring-boot-custom-starter)
