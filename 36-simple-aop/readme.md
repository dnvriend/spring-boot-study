# 36-simple-aop
Aspect Oriented Programming (AOP) is a way for adding behavior to existing code, without modifying the code.

## Concepts
see: https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-introduction-defn

## Spring AOP capabilities
see: [spring-aop capabilities](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-introduction-spring-defn)

TL;DR

- only method execution join points (advising the execution of methods on Spring beans)
- runtime weaving
- AOP implementation' goal is close integration with the Spring IoC, to help solve common problems in Enterprise applications
- Spring supports both @AspectJ annotations and spring-aop xml configuration style
- For @AspectJ, spring uses the library only for pointcut parsing and matching, not for compiling nor weaving, that is done by spring-aop
- Spring AOP is a proxy-based system and differentiates between the proxy object itself (which is bound to this) and the target object behind the proxy (which is bound to target).

## @AspectJ
@AspectJ refers to a style of declaring aspects as regular Java classes annotated with annotations. 

## Enabling @AspectJ support
see: [Enabling aspectj support](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-enable-aspectj-java)

Add the `@EnableAspectJAutoProxy` annotation on a `@Configuration`:

```java
@Configuration
@EnableAspectJAutoProxy
public class AppConfig { }
```

## Creating Aspects
Any class that is an `@Aspect` is automatically detected by Spring. Aspects can have methods and fields. They can also contain pointcut, advice, and introduction (inter-type) declarations.

Note: in order to be automatically detected by Spring classpath scanning, add the `@Component` annotation to an Aspect class.

```java
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemArchitecture {

    /**
     * A join point is in the web layer if the method is defined
     * in a type in the com.xyz.someapp.web package or any sub-package
     * under that.
     */
    @Pointcut("within(com.xyz.someapp.web..*)")
    public void inWebLayer() {}

    /**
     * A join point is in the service layer if the method is defined
     * in a type in the com.xyz.someapp.service package or any sub-package
     * under that.
     */
    @Pointcut("within(com.xyz.someapp.service..*)")
    public void inServiceLayer() {}

    /**
     * A join point is in the data access layer if the method is defined
     * in a type in the com.xyz.someapp.dao package or any sub-package
     * under that.
     */
    @Pointcut("within(com.xyz.someapp.dao..*)")
    public void inDataAccessLayer() {}

    /**
     * A business service is the execution of any method defined on a service
     * interface. This definition assumes that interfaces are placed in the
     * "service" package, and that implementation types are in sub-packages.
     *
     * If you group service interfaces by functional area (for example,
     * in packages com.xyz.someapp.abc.service and com.xyz.someapp.def.service) then
     * the pointcut expression "execution(* com.xyz.someapp..service.*.*(..))"
     * could be used instead.
     *
     * Alternatively, you can write the expression using the 'bean'
     * PCD, like so "bean(*Service)". (This assumes that you have
     * named your Spring service beans in a consistent fashion.)
     */
    @Pointcut("execution(* com.xyz.someapp..service.*.*(..))")
    public void businessService() {}

    /**
     * A data access operation is the execution of any method defined on a
     * dao interface. This definition assumes that interfaces are placed in the
     * "dao" package, and that implementation types are in sub-packages.
     */
    @Pointcut("execution(* com.xyz.someapp.dao.*.*(..))")
    public void dataAccessOperation() {}
    
    // Any join point (method execution only in Spring AOP) where the executing method has an @Transactional annotation:    
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    // Any join point (method execution only in Spring AOP) where the target object has a @Transactional annotation:      
    @Pointcut("@target(org.springframework.transaction.annotation.Transactional)")
    // Any join point (method execution only in Spring AOP) where the declared type of the target object has an @Transactional annotation:
    @Pointcut("@within(org.springframework.transaction.annotation.Transactional)")
    public void transactionalOperation() {}
}
```

## Declaring a Pointcut
see: [Declaring a Pointcut](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-pointcuts)

Pointcuts determine join points of interest and thus enable us to control when advice executes. Spring AOP only supports method execution join points for Spring beans, so you can think of a pointcut as matching the execution of methods on Spring beans. 

A pointcut declaration has two parts: 

1. a signature comprising a name and any parameters,
2. a pointcut expression that determines exactly which method executions we are interested in

In the @AspectJ annotation-style of AOP, a pointcut signature is provided by a regular method definition, and the pointcut expression is indicated by using the @Pointcut annotation (the method serving as the pointcut signature must have a void return type).

The following example defines a pointcut named anyOldTransfer that matches the execution of any method named transfer:

```java
@Pointcut("execution(* transfer(..))")// the pointcut expression
private void anyOldTransfer() {}// the pointcut signature
```

## Advices
An advice is associated with a pointcut expression. An advice runs [@Before](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-advice-before), @After or [@Around](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-ataspectj-around-advice) method executions matched by the pointcut. The pointcut expression may either be a simple reference to a named pointcut or a pointcut expression declared in place.

## @Around advice
see [@Around advice](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-ataspectj-around-advice)

The value returned by the around advice is the return value seen by the caller of the method. 

## Pointcut Designators
see: [supported pointcut designators](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-pointcuts-designators)

- execution: For matching method execution join points. This is the primary pointcut designator to use when working with Spring AOP.
- within: Limits matching to join points within certain types (the execution of a method declared within a matching type when using Spring AOP).
- this: Limits matching to join points (the execution of methods when using Spring AOP) where the bean reference (Spring AOP proxy) is an instance of the given type.
- target: Limits matching to join points (the execution of methods when using Spring AOP) where the target object (application object being proxied) is an instance of the given type.
- args: Limits matching to join points (the execution of methods when using Spring AOP) where the arguments are instances of the given types.
- @target: Limits matching to join points (the execution of methods when using Spring AOP) where the class of the executing object has an annotation of the given type.
- @args: Limits matching to join points (the execution of methods when using Spring AOP) where the runtime type of the actual arguments passed have annotations of the given types.
- @within: Limits matching to join points within types that have the given annotation (the execution of methods declared in types with the given annotation when using Spring AOP).
- @annotation: Limits matching to join points where the subject of the join point (the method being executed in Spring AOP) has the given annotation.

## Annotation meta data
Annotation meta data is non-invasive. The core business logic is not polluted with cross cutting concern code. This makes it easier to reason about, refactor and test the code in isolation.

## Creating a custom annotation
To create a custom annotation we need to create a .java file with the following:

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
}
```

- The `@Target` annotation tells us where our annotation will be applicable. Here we are using ElementType.Method, which means it will only work on methods. If we tried to use the annotation anywhere else, then our code would fail to compile. This behavior makes sense, as our annotation will be used for logging method execution time.
- The `@Retention` just states whether the annotation will be available to the JVM at runtime or not. By default it is not, so Spring AOP would not be able to see the annotation. This is why it's been reconfigured.

## Injecting HttpServletRequest
In Spring-aop, you cannot inject the HttpServletRequest. But, we can use the `RequestContextHolder`.
In every request, the DispatcherServlet binds the current HttpServletRequest to a static 
threadlocal object in the RequestContextHolder. You can retrieve it with the same Thread with:

```java
HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
```

## Advice gets called twice
There are some causes:

1. Do you use JavaConfig or xml? If you're using both, it could be that the Aspect is being processed two times by the Spring IoC container.
2. I don't annotate aspects with @Component annotation, try to remove it, maybe because of that is being processed twice by Spring.
3. The advice is declared as @RequestScope. In that case the same bean is registered twice as an interceptor with different names.  

## Throwing an exception

```java
import org.springframework.web.server;
import org.springframework.http.HttpStatus;

throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found");
```

## Resources
- [spring-core documentation](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html)
- [spring-aop documentation](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop)
- [spring-aop-api documentation](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-api)
- [spring-aop-aspectj documentation](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-ataspectj)
- [spring-aop-examples documentation](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-pointcuts-examples)
- [AspectJ - Programming Guide](https://www.eclipse.org/aspectj/doc/released/progguide/index.html)
- [AspectJ - Developer's Notebook](https://www.eclipse.org/aspectj/doc/released/adk15notebook/index.html)
- [Baeldung - spring-aop](https://www.baeldung.com/spring-aop)
- [Baeldung - An introduction to aspectj](https://www.baeldung.com/aspectj)
- [Baeldung - Implementing a custom spring aop annotation](https://www.baeldung.com/spring-aop-annotation)
- [Baeldung - An introduction to putpoint expressions in spring](https://www.baeldung.com/spring-aop-pointcut-tutorial)
- [Baeldung - Comparing spring aop and aspectj](https://www.baeldung.com/spring-aop-vs-aspectj)
- [Baeldung - An introduction to advice types in spring](https://www.baeldung.com/spring-aop-advice-tutorial)
- [spring-aop v4.3.15 documentation](https://docs.spring.io/spring/docs/4.3.15.RELEASE/spring-framework-reference/html/aop.html)
- [Aspect Oriented Programming with Spring Boot](https://niels.nu/blog/2017/spring-boot-aop.html)
- [How to inject HttpServletRequest in Spring AOP](https://stackoverflow.com/questions/19271807/how-to-inject-httpservletrequest-into-a-spring-aop-request-custom-scenario)
- [Advice is being called twice](https://stackoverflow.com/questions/7900905/spring-aop-advice-is-called-twice)
- [Spring ResponseStatusException](https://www.baeldung.com/spring-response-status-exception)
- [Logging aspect in RESTful web service – spring aop (log requests/responses)](https://makeinjava.com/logging-aspect-restful-web-service-spring-aop-request-response/)
- [adding authentication to spring controller methods with aspectj](http://codebrane.com/blog/2014/01/23/adding-authentication-to-spring-controller-methods-with-aspectj/)
