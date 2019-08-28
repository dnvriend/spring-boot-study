# simple-web-async
Annotating a method of a bean with `@Async` will make it execute in a separate
thread, ie. the caller will not wait for the completion of the called method.

## Async rules
Async as two limitations:

- It must be applied to public methods only: the method must be proxied by the
  async proxy
- self-invocation: calling the @Async method from within the same class won't
  work, self-invocation would bypass the async proxy

## Resources
- [How to do @Async in Spring](https://www.baeldung.com/spring-async)
