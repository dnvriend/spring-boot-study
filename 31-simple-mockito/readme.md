# simple-mockito
A small study project on Mockito

## Introduction
A unit test is a test related to a single responsibility of a single class, often referred to as the System Under Test (SUT). The purpose of unit tests is to verify that the code in an SUT works. A tested object usually talks to other objects known as collaborators. These collaborators need to be created so the tested object can be assigned to them in the test. To make unit testing simpler and allow control of all aspects of the execution context, it is useful to replace the real cooperating objects with their fake replacements called test doubles. They look like the originals, but do not have any dependencies to other objects. Test doubles can also be easily programmed with specific expectations, such as recording any interactions they've had.

To make it clearer, try to imagine code for a typical en- terprise system. Now here's a service with some logic that needs two classes to fulfill its responsibility. Both classes then require a few other classes. One of these other classes could be a DAO, which needs access to a database, while yet another requires a message queue. It would be quite an effort to create that hierarchy and provide required resources. There could also be problems while running that kind of test, e.g., long startup times or the inability to test multiple developer stations simultaneously. Using Mocks, though, the same test could be much cleaner and faster.

Test doubles can be divided into a few groups:
There are several types of test doubles to do Unit Testing. Test Doubles are replacement objects for 

- Dummy: an empty object passed in an invocation (usually only to satisfy a compiler when a method ar- gument is required)
- Fake: an object having a functional implementation, but usually in a simplified form, just to satisfy the test (e.g., an in-memory database)
- Stub: an object with hardcoded behavior suitable for a given test (or a group of tests)
- Mock: an object with the ability to a) have a programmed expected behavior, and b) verify the interactions occurring in its lifetime (this object is usually created with the help of mocking framework)
- Spy: a mock created as a proxy to an existing real object; some methods can be stubbed, while the un- stubbed ones are forwarded to the covered object

## Setup
Mockito provides an implementation for JUnit5 extensions:

```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>
```

## Create test class
To create a test class:

```java
// Extension that initializes mocks and handles strict stubbings.
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Test
    void foo() {
    }
}
```

The ExtendWith is an annotation that is used to register an Extension for the annotated test class or test method. You can extend a test class to add Mockito functionality but also with other extensions like `@ExtendWith(SpringExtension.class)` which is what `@SpringBootTest` is doing.    

## Import Mockito
Import the following package:

```
import static org.mockito.Mockito.*;
```

## The Mockito class
The Mockito class enables mock creation, verification and stubbing. It is the main class that you use when you interact with mocks. It contains static methods (that you can import), and provides the `when/thenReturn` and `doXYZ/willXYZ` behavior. It also provides `verify` and `lenient` behavior. 

For asserting behavior you would use `Mockito.verify`, but for asserting values, you would use a different library like eg. JUnit or AssertJ.  

## Mock behavior
One of the basic functions of mocking frameworks is an ability to return a given value when a specific method is called. It can be done using `Mockito.when()` in conjunction with `thenReturn()` . This process of defining how a given mock method should behave is called stubbing.

Example:

```
// you can mock concrete classes, not only interfaces
LinkedList mockedList = mock(LinkedList.class);

// stubbing appears before the actual execution
when(mockedList.get(0)).thenReturn("first");

// the following prints "first"
System.out.println(mockedList.get(0));

// the following prints "null" because get(999) was not stubbed
System.out.println(mockedList.get(999));
```

Other methods:

```
thenReturn(T)
thenThrow()
thenCallRealMethod()
``` 

## Argument Matching
In the example above, we see the mocked behavior `when(mockedList.get(0)).thenReturn("first");` that states that when 
`.get(0)` is called, it returns `"first"`. The default behavior os argument matching that mockito uses is using the `equals()`
method. To provide a wider range, mockito provides a set of build-in matchers, defined in Matchers and AdditionalMatchers classes:

```
import static org.mockito.ArgumentMatchers;
import static org.mockito.AdditionalMatchers;
```

ArgumentMatchers provide matchers like `any()`, `anyInt()` etc.

## Multiple calls to the same mock
We can return different values for subsequent calls to the same method. Values can be mixed with exceptions. The last/value is used for all following calls:

```
WaterSource waterSource = mock(WaterSource.class);
given(waterSource.getWaterPressure()).willReturn(3, 5);
assertEquals(waterSource.getWaterPressure(), 3);
assertEquals(waterSource.getWaterPressure(), 5);
assertEquals(waterSource.getWaterPressure(), 5);
```

## Stubbing Void methods
For stubbing Void methods we use `doXYZ()/WillXYZ()`:

## Changing the mock default return value
Mockito enables us to redefine a default value returned from non-stubbed methods:

- RETURNS_DEFAULTS (default): Returns a default "empty" value (e.g., null, 0, false, empty collection) 
- RETURNS_SMART_NULLS: Creates a spy of a given object
- RETURNS_MOCKS: Returns a default "empty" value, but a mock instead of null
- RETURNS_DEEP_STUBS: Allows for a simple deep stubbing (e.g., Given(ourMock.getObject().getValue()). willReturn(s))
- CALLS_REAL_METHODS: Call a real method of spied object

```
PlantWatererplantWatererMock=mock(PlantWaterer.class,
Mockito.RETURNS_DEEP_STUBS);
given(plantWatererMock.getWaterSource().getWaterPressure()).willReturn(5);
@Mock(answer=Answers.RETURNS_SMART_NULLS)
privatePlantWatererplantWatererMock;
```

## Resetting mocks

In some rare cases (like using a mock as a bean in an IoC container) you may need to reset a mock using the `Mockito.reset(T ... mocks)` static method. This causes the mock to forget all previous behavior and interactions.

## Limitations
There are some limitations. Mockito cannot:
                            
- mock final classes
- mock enums
- mock final methods
- mock static methods
- mock private methods
- mock hashCode() and equals()

PowerMock and JMockit can be used with code that cannot be mocked with pure Mockito.

## BDDMockito
There is also `import static org.mockito.BDDMockito.*;` that provides `given().willReturn()` semantics.

## Mockito Annotations
Mockito annotations can be used in unit test classes. There are two ways to initialize these fields: set `@ExtendWith(MockitoExtension.class)` on the test class and calling `MockitoAnnotations.initMocks(object)` in the object constructor. 

```
@Mock: Mark a field as a mock
@Spy: Wraps field instance in an spy object
@Captor: Argument capture on fields
@InjectMocks: Inject mock objects for fields using either constructor, setter or field injection.
```

## Spring Boot @MockBean Annotation
We can use the `@MockBean` to add mock objects to the Spring application context. The mock will **replace** any existing bean of the same type in the application context.

If no bean of the same type is defined, a new one will be added. This annotation is useful in integration tests where a particular bean – for example, an external service – needs to be mocked.

## Spring Boot @SpyBean Annotation
The annotation `@SpyBean` can be used to wrap any existing bean with a Mockito spy. 

## NPE and annotations
When you get NPEs, we probably forgot to enable Mockito annotations with `@ExtendWith(MockitoExtension.class)`

## Verify behavior
Once created, a mock remembers all operations performed on it. Important from the SUT perspective, these operations can easily be verified. In the basic form, use `Mockito.verify(T mock)` on the mocked method.

By default, Mockito checks if a given method (with given arguments) was called once and only once. This can be modified using a VerificationMode. Mockito provides the number of very meaningful verification modes. It is also possible to create a custom verification mode.

```
// mock creation
List mockedList = mock(List.class);

// using mock object - it does not throw any "unexpected interaction" exception
mockedList.add("one");
mockedList.clear();

// selective, explicit, highly readable verification
verify(mockedList).add("one");
verify(mockedList).clear();

WaterSourcewaterSourceMock=mock(WaterSource.class);
waterSourceMock.doSelfCheck();
// called only once
verify(waterSourceMock).doSelfCheck();

// never called
import static org.mockito.Mockito.never;
verify(waterSourceMock,never()).doSelfCheck();
// called 2 times
verify(waterSourceMock,times(2)).getWaterPressure();
// called at least once
verify(waterSourceMock,atLeast(1)).getWaterTemperature();
```

## Spying on real objects
With Mockito, you can use real objects instead of mocks by replacing only some of their methods with the stubbed ones. Usually there is no reason to spy on real objects, and it can be a sign of a code smell, but in some situations (like working with legacy code and IoC containers) it allows us to test things impossible to test with pure mocks.

When working with spies it is required to use the `willXXX..given/doXXX..when` methods family instead of `given .. willXXX/when.. thenXXX`. This prevents unnecessary calls to a real method during stubbing.

__Warning__: While spying, Mockito creates a copy of a real object, and therefore all interactions should be passed using the created spy.

```
Flower realFlower = new Flower();
realFlower.setNumberOfLeafs(ORIGINAL_NUMBER_OF_LEAFS);
Flower flowerSpy = spy(realFlower);
willDoNothing().given(flowerSpy).setNumberOfLeafs(anyInt());
flowerSpy.setNumberOfLeafs(NEW_NUMBER_OF_LEAFS); //stubbed, should do nothing
verify(flowerSpy).setNumberOfLeafs(NEW_NUMBER_OF_LEAFS);
assertEquals(flowerSpy.getNumberOfLeafs(),ORIGINAL_NUMBER_OF_LEAFS); //value was not changed
```

## Testing Stack
Testing without Spring is easy if you have your classes designed correctly, ie. using constructor based injection and using eg. Mockito to inject mocked objects.

Often you need to move a little further up the stack and start to write integration tests that do involve Spring. Luckily, Spring Framework has the spring-test module to help here. There is a single `@SpringBootTest` annotation to use for regular tests, as well as some specialized variants for testing slices of your application.

## Testing and Spring Boot
pring Boot provides a number of utilities and annotations to help when testing your application. Test support is provided by two modules: `spring-boot-test` contains core items, and `spring-boot-test-autoconfigure` supports auto-configuration for tests.

Most developers use the `spring-boot-starter-test` “Starter”, which imports both Spring Boot test modules as well as JUnit, AssertJ, Hamcrest, and a number of other useful libraries.

## Testing slices
Spring Boot’s auto-configuration system works well for applications but can sometimes be a little too much for tests. It often helps to load only the parts of the configuration that are required to test a “slice” of your application. For example, you might want to test that Spring MVC controllers are mapping URLs correctly, and you do not want to involve database calls in those tests, or you might want to test JPA entities, and you are not interested in the web layer when those tests run.

The spring-boot-test-autoconfigure module includes a number of annotations that can be used to automatically configure such “slices”. Each of them works in a similar way, providing a @…​Test annotation that loads the ApplicationContext and one or more @AutoConfigure…​ annotations that can be used to customize auto-configuration settings.

A list of slices can be found [here](https://docs.spring.io/spring-boot/docs/current/reference/html/test-auto-configuration.html).

- [DataJpaTest](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-jpa-test)
- DataJdbcTest
- JdbcTest: 
- JsonTest
- RestClientTest
- WebFluxTest
- WebMvcTest

## WebMvcTest
[WebMvcTest](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests) is used to  test whether Spring MVC controllers are working as expected. @WebMvcTest auto-configures the Spring MVC infrastructure and limits scanned beans to @Controller, @ControllerAdvice, @JsonComponent, Converter, GenericConverter, Filter, WebMvcConfigurer, and HandlerMethodArgumentResolver. Regular @Component beans are not scanned when using this annotation.

Often, @WebMvcTest is limited to a single controller and is used in combination with @MockBean to provide mock implementations for required collaborators.

@WebMvcTest also auto-configures MockMvc. Mock MVC offers a powerful way to quickly test MVC controllers without needing to start a full HTTP server.

## Spring Test Web Components
Spring provides a set of Servlet API 3.0 mock objects that are targeted at usage with the Spring Web MVC Framework. Some of these objects are MockHttpServletRequest, MockHttpServletResponse, MockFilterChain and so on.  

## MockMvc
MockMvc is the main entry point for server-side Spring MVC test support. Using MockMvc, a server is not started, but the layer below the server, where Spring handles the incoming HTTP request and hands it off to your controller. That way, almost the full stack is used, and your code will be called exactly the same way as if it was processing a real HTTP request, but without the cost of starting the server. To do that we will use Spring’s MockMvc, and we can ask for that to be injected in our test class. 

## Integration Test
Run a single integration test:

```
# run only integration test
mvn -Dit.test="HelloWorldIT" surefire:integration-test

# run unit and integration test
mvn -Dit.test="HelloWorldIT" integration-test
```

## Resources
- [Baeldung Mockito Series](https://www.baeldung.com/mockito-series)
- [Mockito](https://site.mockito.org/)
- [Mockito JavaDoc](https://static.javadoc.io/org.mockito/mockito-core/3.0.0/org/mockito/Mockito.html)
- [Mockito Manual](https://dzone.com/refcardz/mockito?chapter=1)
- [Testing improvements in Spring](https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)
- [Testing the web layer](https://spring.io/guides/gs/testing-web/)
- [Testing Spring Filters without Mocking Pain](http://engineering.pivotal.io/post/testing-spring-filters-without-pain/)
- [Integration test for authentication filter with Spring Boot](https://stackoverflow.com/questions/40071707/integration-test-for-authentication-filter-with-spring-boot)
- [Spring Boot Test Data Layer Example with @DataJPATest](https://hellokoding.com/spring-boot-test-data-layer-example-with-datajpatest/)
- [Testing JPA Queries with @DataJpaTest](https://reflectoring.io/spring-boot-data-jpa-test/)
- [Integration Testing of Spring MVC Applications: Write Clean Assertions with JsonPath](https://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-write-clean-assertions-with-jsonpath/)
