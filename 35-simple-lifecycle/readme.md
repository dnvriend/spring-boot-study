# 35-simple-lifecycle

## Application Life Cycle
Depending what kind of Spring application you start, most applications have the same life cycle, depending on the starting point. 

A spring-boot application uses `SpringApplication.run()` as the starting point, that boots a Spring application. 

## Types of life cycle events
The Spring bean factory (ApplicationContext) is responsible for managing the life cycle of beans created through the spring container. The life cycle consists of call back methods which can be categorized broadly into two groups:

- post initialization call back methods
- pre destruction call back methods

## Life cycle

1. Container is in shutdown: 
  1.1 for each `DisposableBean` interface, the `destroy()` method is being called.
2. Container is initializing:
  - Bean is instantiated
  - Populate bean properties  
  - `BeanNameAware.setBeanName()` is called
  - `BeanFactoryAware.setBeanFactory()` is called
  - `ApplicationContextAware.setApplicationContext()` is called
  - (pre-init) `BeanPostProcessor.postProcessBeforeInitialization()` is called
  - `InitializingBean.afterPropertiesSet()` is called
  - custom init method is called
  - (post-init) `BeanPostProcessor.postProcessAfterInitialization()` is called
  - Bean is ready
  
## Bean Life cycle callback methods
Spring provides four ways of controlling life cycle events of a bean:

1. `InitializingBean` and `DisposableBean` callback interfaces;
  1.1 `InitializingBean.afterPropertiesSet()` is called right after all the properties have been set by the container,
  1.2 `DisposableBean.destroy()` is called when the container destroys the bean. 
2. `Aware` interfaces for specific behavior
3. Custom `init()` and `destroy()` methods in a bean configuration file
4. Life cycle annotations
  4.1 `@PostConstruct`: invoked after the bean has been constructed using default constructor and just before it’s instance is returned to requesting object.
  4.2 `@PreDestroy`: is called just before the bean is about be destroyed inside bean container.

## Aware interfaces
The `Aware` interfaces allow beans to indicate that they require a certain infrastructure dependency 
like `ApplicationContextAware`,  `ApplicationEventPublisherAware`, `BeanClassLoaderAware`, `BeanFactoryAware`,
`BeanNameAware`, `BootstrapContextAware`,  

## Events
The following events can be generated:

- ContextRefreshedEvent
- ServletWebServerInitializedEvent
- ApplicationStartedEvent
- ApplicationReadyEvent
- ContextClosedEvent

Other application events:

- ServletRequestHandledEvent

## FactoryBean
A factory bean is a bean that serves as a factory for creating other beans within the IoC container. 

To create a factory bean, all you have to do is to implement the FactoryBean interface by your creator bean class which will be creating actual other beans. Or to keep it simple, you can extend AbstractFactoryBean class.

Factory beans are mostly used to implement framework facilities. Here are some examples:

- When looking up an object (such as a data source) from JNDI, you can use JndiObjectFactoryBean.
- When using classic Spring AOP to create a proxy for a bean, you can use ProxyFactoryBean.
- When creating a Hibernate session factory in the IoC container, you can use LocalSessionFactoryBean.

In most cases, you rarely have to write any custom factory beans, because they are framework-specific and cannot be used outside the scope of the Spring IoC container.

## BeanPostProcessor Interface
The [BeanPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html) is a factory hook that allows for custom modification of new bean instances, e.g. checking for marker interfaces or wrapping them with proxies.

 ApplicationContexts can **autodetect** BeanPostProcessor beans in their bean definitions and apply them to any beans subsequently created. Plain bean factories allow for programmatic registration of post-processors, applying to all beans created through this factory.
                         
 Typically, post-processors that populate beans via marker interfaces or the like will implement`postProcessBeforeInitialization`, while post-processors that wrap beans with proxies will normally implement`postProcessAfterInitialization`.
                         
Typically spring’s DI container does following things to create a bean, you request for:

- Create the bean instance either by a constructor or by a factory method
- Set the values and bean references to the bean properties
- Call the setter methods defined in the all the aware interfaces
- Pass the bean instance to the `BeanPostProcessor.postProcessBeforeInitialization()` method of each bean post processor
- `InitializingBean.afterPropertiesSet()` is called
- Pass the bean instance to the `BeanPostProcessor.postProcessAfterInitialization()` method of each bean post processor
- The bean is ready to be used

When the container is shut down, call the destruction callback methods

## BeanFactoryPostProcessor
The [BeanFactoryPostProcessor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanFactoryPostProcessor.html) allows for custom modification of an application context's bean definitions, adapting the bean property values of the context's underlying bean factory.

Application contexts can **autodetect** BeanFactoryPostProcessor beans in their [@Bean](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html) definitions and apply them before any other beans get created. These methods don't have to be static, but can be. If you provide a BeanFactoryProcessor with a static @Bean method, the method will be called *before* any `Aware` interfaces. If you don't use a static @Bean method, any `Aware` interfaces will be called before the BeanFactoryPostProcessor. BeanFactoryPostProcessor providing @Bean methods should be static! These methods can be invoked without instantiating the containing [@Configuration](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html) class. Note that you cannot autowire eg. `ApplicationContext`, that will be null if you do. Any @Bean method providing a BeanFactoryPostProcessor must provide a default constructor. You cannot use constructor injection. 

Useful for custom config files targeted at system administrators that override bean properties configured in the application context.

see: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html
Special consideration must be taken for @Bean methods that return Spring BeanFactoryPostProcessor (BFPP) types. Because BFPP objects must be instantiated very early in the container lifecycle, they can interfere with processing of annotations such as @Autowired, @Value, and @PostConstruct within @Configuration classes. To avoid these lifecycle issues, mark BFPP-returning @Bean methods as static. For example:

See [PropertyResourceConfigurer](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/config/PropertyResourceConfigurer.html) and its concrete implementations for out-of-the-box solutions that address such configuration needs.

A BeanFactoryPostProcessor may interact with and modify bean definitions, but never bean instances. Doing so may cause premature bean instantiation, violating the container and causing unintended side-effects. If bean instance interaction is required, consider implementing BeanPostProcessor instead.

## AbstractApplicationContext
[AbstractApplicationContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/AbstractApplicationContext.html) is an abstract implementation of ApplicationContext. It implements common context functionality. ApplicationContext detects special beans defined in its internal bean factory. It can automatically detect and register:

- BeanFactoryPostProcessor (L531),
- BeanPostProcessor (L534),
- ApplicationListener (L546)

## Resources
- [spring-core documentation](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html)
- [spring-bean lifecycle](https://howtodoinjava.com/spring-core/spring-bean-life-cycle/)
- [Spring events](https://www.baeldung.com/spring-events)