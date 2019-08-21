# 16-simple-web-filter
To create a filter, follow these steps:

- Create a class that implements `javax.serlvet.Filter`

## Filter ordering
@Order can be any number between Integer.MIN_VALUE and Integer.MAX_VALUE as defined in Ordered interface. Integer.MAX_VALUE basically means that it will have THE LOWEST PRIORITY and MAX_VALUE -1 has one higher priority than previous one. HIGHER value has LOWER precedence.

## Resources
- [How to Define a Spring Boot Filter?](https://www.baeldung.com/spring-boot-add-filter)
- [Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- [Spring Boot Add Filter](https://www.baeldung.com/spring-boot-add-filter)