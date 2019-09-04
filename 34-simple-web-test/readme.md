# 34-simple-web-test

## Testing
There are several test slices:

- @SpringBootTest: provides auto-configuration, loading of beans, etc
- @WebMvcTest: does not support auto-configuration, but supports testing controllers and mocking
- @SpringJunitConfig: basic spring junit support, is a bit faster than SpringBootTest

