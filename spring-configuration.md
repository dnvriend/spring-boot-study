# Configuration

## Excluding Autoconfiguration
A `@SpringBootApplication` can exclude configurations by configuring the
`exclude` property on `@SpringBootApplication` like below:

```java
@SpringBootApplication(
  exclude = { HibernateJpaAutoConfiguration.class,
  DataSourceAutoConfiguration.class}
)
```


