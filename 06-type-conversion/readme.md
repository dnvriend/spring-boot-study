# 06-type-conversion
Type conversion in Spring in kinda simple. You have to inject a `ConversionService` that can (possibly) convert a 
type for you.The service is used eg. by spring-mvc (REST) for converting String to request param/value conversion. 
If you want to use the service, you have to mock the ConversionService. I don't know how handy this indirection is.
Most conversions are stateless, and a converter (ie. Function) or a Service (just annotate any bean with @Component/@Service)
and inject it, and is maybe better solution, without the indirection.

Converters must also be registered by adding a `WebConfig` 
   
## Resources
- [Baeldung - Type Conversion](https://www.baeldung.com/spring-type-conversions)
