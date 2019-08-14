# 28-simple-spring-interceptor

## Introduction
In order to understand the interceptor, let’s take a step back and look at the [HandlerMapping](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/HandlerMapping.html). This maps a `method` to an `URL`, so that the DispatcherServlet will be able to invoke it when processing a request. The DispatcherServlet uses the [HandlerAdapter](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/HandlerAdapter.html) to actually invoke the method. 

The [HandlerInterceptor](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/HandlerInterceptor.html) performs actions before handling, after handling or after completion of a request.

The interceptor can be used for cross-cutting concerns and to avoid repetitive handler code like: logging, changing globally used parameters in Spring model etc.

## How to use
Interceptors must implement the [HandlerInterceptor](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/HandlerInterceptor.html) interface, or extend the [HandlerInterceptorAdapter](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/handler/HandlerInterceptorAdapter.html) for simplified implementation of pre-only/post-only interceptors. 

The main difference between HandlerInterceptor and HandlerInterceptorAdapter is that in the first one we need to override all three methods: preHandle(), postHandle() and afterCompletion(), whereas in the second we may implement only required methods.

The HandlerInterceptor interface specifies three main methods:

- prehandle(): Boolean - called before the actual handler is executed, but the view is not generated yet, return true if the request should be processed further,
- postHandle(): called after the handler is executed
- afterCompletion(): called after the complete request has finished and view was generated

These three methods provide flexibility to do all kinds of pre- and post-processing.

A Handler Interceptor is registered to the DefaultAnnotationHandlerMapping bean, which is responsible for applying interceptors to any class marked with a @Controller annotation. Moreover, you may specify any number of interceptors in your web application.

## Configure
Interceptors must be added by a @Configuration class that extends WebMvcConfigurer, and adds the interceptor by overriding the `addInterceptors()` method.

```java
@Configuration  
public class MyConfig extends WebMvcConfigurer  {  
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/account/login");
    }
} 
```

## What about Filters?
TL;DR: A lot of things Filters do, Interceptors can do more easily and more elegantly; the Filter API is quite old and not very intuitive.

That being said, Filters can be used for generic tasks across all requests then a Filter can be used. When you need specific behavior for a specific target handler, then an Interceptor could be the better candidate.

## Resources
- [Introduction to Spring MVC HandlerInterceptor](https://www.baeldung.com/spring-mvc-handlerinterceptor)
- [Display auto configuration](https://www.baeldung.com/spring-boot-auto-configuration-report)
- [SO](https://stackoverflow.com/questions/7290413/how-can-i-lookup-the-method-being-called-on-a-handler-from-a-spring-handlerinter)

