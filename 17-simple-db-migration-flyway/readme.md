# 17 - simple db migration flyway

## Configuation
Flyway has some configurations to consider:

```bash
spring.flyway.enabled: true
spring.flyway.locations: "classpath:db/migration/{vendor}"
spring.flyway.check-location: false
spring.flyway.baseline-on-migrate: true
```

Most configuration items are self-explanatory. The `{vendor}` is used to have migration scripts for different databases
like h2 or mysql. Depending on the database dialect, a different directory will be chosen.

The `baseline-on-migrate` is a feature that must be enabled when a schema is already present with possible tables present. 
Flyway will create the `flyway_schema_history` table and populate the table with an an initial entry which is the baseline entry
and has version 1. When migration scripts must be applied for a populated database, scripts with a version number higher 
than 1 must be present. Migration scripts with version number 1 will be skipped. It is therefor mandatory that you start your migration
scripts version numbers with version 2 or higher for an existing schema.

## Resources
- [Flyway - Github](https://github.com/flyway/)
- [FLyway - Documentation](https://flywaydb.org/)
- [Execute Flyway Database Migrations on Startup](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html)
- [FlywayProperties](https://github.com/spring-projects/spring-boot/blob/v2.1.6.RELEASE/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/flyway/FlywayProperties.java)