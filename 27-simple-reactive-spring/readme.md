# 27-simple-reactive-spring

## Introduction
The reactive variant of Spring is called Spring reactor. It is an implementation of the Reactive Streams interface.
As you know, the reactive streams interface is quite technical and dragons can be found everywhere. That is why
creating an implementation is better left to the experts and a reactive framework like RxJava, Akka Streams or in
this case for Spring, project reactor can be used. 

## What is project reactor
Project reactor is RxJava-like. It provides reactive types like `Flux[T]`, which can be best thought of as `Stream[T]`. 
There is also the `Mono[T]` which can be best thought of as `Future[T]`. Flux and Mono are publishers. 

These reactive types have combinators in order to operate on the elements that flow though the network. There are also combinators that operate on streams. 

## Common operations
Project reactor provides operations like merge, zip, publish (fan out). There is one thing you must not forget.
Nothing happens until you `subscribe()`!!

## Custom Thread Pool in Java 8 Streams

```
ForkJoinPool forkJoinPool = new ForkJoinPool(2);
forkJoinPool.submit(() ->
    //parallel task here, for example
    IntStream.range(1, 1_000_000).parallel().filter(PrimesPrint::isPrime).collect(toList())
).get();
```

## R2DBC
The primary goal of the Spring Data project is to make it easier to build Spring-powered applications that use data access technologies. Spring Data R2DBC offers the popular Repository abstraction based on R2DBC. R2DBC is the abbreviation for `Reactive Relational Database Connectivity`, an incubator to integrate relational databases using a reactive driver.

## Resources

- [Doing stuff with Spring WebFlux - Lanky Dan](https://lankydan.dev/2018/03/15/doing-stuff-with-spring-webflux)
- [Testing Reactive Streams Using StepVerifier and TestPublisher](https://www.baeldung.com/reactive-streams-step-verifier-test-publisher)
- [Reactive Spring - Josh Long, Mark Heckler](https://www.youtube.com/watch?v=l7VBdWhtl7A)
- [Reactor 3.0, a JVM foundation for Java 8 and Reactive Streams](https://www.youtube.com/watch?v=ctZGFTI3XI8)
- [Getting Started with R2DBC](https://www.youtube.com/watch?v=qwF6v6FN_Uc)
- [Reactive Relational Database Connectivity](https://www.youtube.com/watch?v=idApf9DMdfk)
- [Spring Tips: Reactive Transactions in MongoDB and R2DBC](https://www.youtube.com/watch?v=9henAE6VUbk)
- [RSocket](http://rsocket.io/)
- [RD2BC](https://r2dbc.io/)
- [Thread Pool](https://www.baeldung.com/thread-pool-java-and-guava)
- [Java 8 Parallel Streams and Thread Pools](https://www.baeldung.com/java-8-parallel-streams-custom-threadpool)
- [Custom Thread Pool in Java 8 Streams](https://stackoverflow.com/questions/21163108/custom-thread-pool-in-java-8-parallel-stream)
- [How to specify custom thread pool in Java 8 Streams](https://blog.krecan.net/2014/03/18/how-to-specify-thread-pool-for-java-8-parallel-streams/)
- [About the official documentation for custom thread pool for Java 8 Streams](https://stackoverflow.com/questions/24629247/where-does-official-documentation-say-that-javas-parallel-stream-operations-use)
- [New default for ForkJoinPool.commonPool on systems with SecurityManagers](http://jsr166-concurrency.10961.n7.nabble.com/New-default-for-ForkJoinPool-commonPool-on-systems-with-SecurityManagers-td10447.html)
- [spring-data-r2dbc](https://github.com/spring-projects/spring-data-r2dbc)
- [spring-data-r2dbc reference guide](https://docs.spring.io/spring-data/r2dbc/docs/1.0.0.M2/reference/html/#reference)
- [r2dbc-postgresql](https://github.com/r2dbc/r2dbc-postgresql)
- [reactor netty reference guide](https://projectreactor.io/docs/netty/snapshot/reference/index.html)
- [project reactor docs](https://projectreactor.io/docs/)
- [Baeldung - r2dbc](https://www.baeldung.com/r2dbc)
- [Spring Framework 5.2.0.M2 available now](https://spring.io/blog/2019/05/10/spring-framework-5-2-0-m2-5-1-7-5-0-14-and-4-3-24-available-now)
- [Spring data moore released](https://spring.io/blog/2019/05/14/spring-data-moore-m4-lovelace-sr8-and-ingalls-sr22-released)
- [Spring data r2dbc for sql server](https://lankydan.dev/spring-data-r2dbc-for-microsoft-sql-server)
- [Spring r2dbc with postgres](https://dzone.com/articles/asynchronous-rdbms-access-with-spring-data-r2dbc)
- [Spring tips - Jasync SQL](https://spring.io/blog/2019/03/20/spring-tips-reactive-mysql-support-with-jasync-sql-and-r2dbc)
- [Reactive programming and relational databases](https://spring.io/blog/2018/12/07/reactive-programming-and-relational-databases)
- [Reactive Streams](https://www.reactive-streams.org/)

