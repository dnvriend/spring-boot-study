# 22-simple-jpa-mysql

## Enable a profile
To enable a profile, launch the VM with the 
```text
-Dspring.profiles.active=mysql-default
-Dspring.profiles.active=h2-default
```

## MySQL storage engine
MySQL supports [MyISAM](http://dev.mysql.com/doc/refman/5.1/en/myisam-storage-engine.html) and [InnoDB](http://dev.mysql.com/doc/refman/5.1/en/innodb-storage-engine.html) storage engines. If you need transactions and
row-level locking, you use InnoDB.

Using `MySQLInnoDBDialect`, Hibernate appends `type=InnoDB` to the table creation statement. This explicitly creates an InnoDB table. `MySQLDialect` does not append an engine string, thus, would create a MyISAM table.

However, you can also change the default storage engine of the MySQL server by using the following line in your `my.cnf`, MySQL configuration, file.

```
default-storage-engine=innodb
```

## Hibernate Naming Strategy
Hibernate uses [two different naming strategies](https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#naming) to map names from the object model to the corresponding database model:
 
 - ImplicitNamingStrategy: Java class and properties to logical names (Spring defaults to SpringPhysicalNamingStrategy)
 - PhysicalNamingStrategy: logical names to SQL tables and columns (Spring defaults to SpringImplicitNamingStrategy)

By default, Spring Boot configures the physical naming strategy with [SpringPhysicalNamingStrategy](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot/src/main/java/org/springframework/boot/orm/jpa/hibernate/SpringPhysicalNamingStrategy.java). This implementation provides the same table structure as Hibernate 4: 

- all dots are replaced by **underscores**,
- camel casing is replaced by **underscores**,
- all table names are generated in **lower case**,

For example: `TelephoneNumber` entity is mapped to the `telephone_number` table.

## Customizing the Naming strategy
The fully qualified class name of the `implicit strategy` an the `physical` implementations can be configured in `application.yml`:

- **spring.jpa.hibernate.naming.implicit-strategy**: 
  - (default) `org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy`
  - (Legacy Hibernate compliant) `org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyHbmImpl`
  - (JPA1.0 compliant) `org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl`
  - (JPA2.0 compliant) `org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl`
  - (For nested properties) `org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl`
- **spring.jpa.hibernate.naming.physical-strategy**: 
  - (default) `org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy`
  - (hibernate) `org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl`  

Alternatively, if ImplicitNamingStrategy or PhysicalNamingStrategy beans are available in the application context, Hibernate will be automatically configured to use them.

Of course, we can create our own implementation if the defaults do not work for our persistency model.

## Implicit Naming Strategy
Hibernate uses a logical name to map an entity or attribute name to a table or column name. This name can be customized in two ways: it can be derived automatically by using an ImplicitNamingStrategy or it can be defined explicitly by using annotations.

The ImplicitNamingStrategy governs how Hibernate derives a logical name from our Java class and property names. We can select from four built-in strategies, or we can create our own.

The `org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl` conforms to the JPA standard, which means that the logical names will be the same as the Java class and property names.

If you want to deviate from this strategy for a specific entity, you can use the `@Table` and `@Column` annotations. 

```java
@Entity
@Table(name = "Customers")
public class Customer {
 
    @Id
    @GeneratedValue
    private Long id;
 
    private String firstName;
 
    private String lastName;
 
    @Column(name = "email")
    private String emailAddress;    
}
``` 

The configuration above configures the logical name that Hibernate will  use to be:

```text
Customer -> Customers
firstName -> firstName
lastName -> lastName
emailAddress -> email
```

## Physical Naming Strategy
Hibernate uses the Physical Naming Strategy to map the logical names to a SQL table and its columns.

By default, the physical name will be the same as the logical name that we specified in the previous section. 

If we want to customize the physical names, we can create a custom PhysicalNamingStrategy class.

## ImplicitNamingStrategyComponentPathImpl
The `ImplicitNamingStrategyComponentPathImpl` naming strategy prefixes the logical name of the class property with the name
of the field. Eg. when you have an embedded primary key, the strategy will prefix the name of the fields of the primary key
with the name of the field. Eg:

The composite key:

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class TripleKeyId implements Serializable {
    private String k1;
    private String k2;
    private String k3;
    @Convert(converter = DateTimeAttributeConverter.class)
    private DateTime start;
    @Convert(converter = DateTimeAttributeConverter.class)
    private DateTime end;
}
```

The entity:

```java
package com.github.dnvriend.repositories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "triple")
@Table(indexes = {
        @Index(name = "KEYS_INDEX", columnList = "id_k1, id_k2, id_k3"),
        @Index(name = "KEYS_INDEX_START_ASC", columnList = "id_k1, id_k2, id_k3, id_start ASC"),
        @Index(name = "KEYS_INDEX_START_DESC", columnList = "id_k1, id_k2, id_k3, id_start DESC")
})
public class Triple {
    @EmbeddedId
    private TripleKeyId id;
    private String value;
}
```

The `ImplicitNamingStrategyComponentPathImpl` will create columns that are prefixed with `id_k1` and so on. The default strategy
will create columns with just the name of the primary key, so `k1` and so on. Use this strategy if your data model looks like this. 

## Resources
- [Hibernate 5 Naming Strategy Configuration](https://www.baeldung.com/hibernate-naming-strategy)
- [Hibernate Field Naming with Spring Boot](https://www.baeldung.com/hibernate-field-naming-spring-boot)
- [Spring MVC custom data binder](https://www.baeldung.com/spring-mvc-custom-data-binder)
- [JPA - Attribute Converters](https://www.baeldung.com/jpa-attribute-converters)
- [JPA - Java8 examples](https://github.com/spring-projects/spring-data-examples/tree/master/jpa/java8)
- [JPA - Composite Primary Key](https://www.baeldung.com/jpa-composite-primary-keys)
- [JPA - Data Query by Example](https://www.baeldung.com/spring-data-query-by-example)
- [spring-data-jpa - Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Hibernate User Guide - Naming](https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#naming)
- [Spring Boot Reference - Configuring Hibernate Naming Strategy](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-data-access.html)
