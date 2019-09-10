# 34-simple-web-test

## Testing
There are several test slices:

- @SpringBootTest: provides auto-configuration, loading of beans, etc
  - @EnableAutoConfiguration
  - @AutoConfigureMockMvc
  - @TestPropertySource(properties= { "com.github.dnvriend.foo.enable=true", "com.github.dnvriend.bar.enable=true"})
- @WebMvcTest: does not support auto-configuration, but supports testing controllers and mocking
- @SpringJunitConfig: basic spring junit support, is a bit faster than SpringBootTest
- @ExtendWith(MockitoExtension.class) => for JUnit test that runs with Mockito, no Spring at all 
  - @Mock method parameters
  - mark with //given //when //then
  
## Mockito mock HttpServletRequest

```java
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public HttpServletRequest buildMockRequest() {
    ServletContext servletContext = new MockServletContext();
    return MockMvcRequestBuilders.get(URI.create("/")).buildRequest(servletContext);
}

public HttpServletRequest buildMockRequestWithAttribute() {
    HttpServletRequest request = buildMockRequest();
    request.setAttribute("foo", "bar");
    return request;
}
```


## Resourcs
- [Mockito void methods](https://www.baeldung.com/mockito-void-methods)
- [Spring Response Status Exception](https://www.baeldung.com/spring-response-status-exception)
- [Spring Response with Spring 5](https://www.baeldung.com/spring-response-header)
