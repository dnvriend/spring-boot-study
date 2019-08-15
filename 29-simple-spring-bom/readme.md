# 29-simple-spring-bom
BOM stands for Bill Of Materials. A BOM is a special kind of POM that is used to control the versions of a project’s dependencies and provide a central place to define and update those versions.

BOM provides the flexibility to add a dependency to our module without worrying about the version that we should depend on.

TL;DR: we can then either inherit or import dependencies. BOM has benefits over parent poms.

## Using a BOM
Put the BOM in the dependencyManagement section:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>baeldung</groupId>
            <artifactId>Baeldung-BOM</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## Overwriting BOM Dependency
The order of precedence of the artifact’s version is:

- The version of the artifact’s direct declaration in our project pom
- The version of the artifact in the parent project
- The version in the imported pom, taking into consideration the order of importing files dependency mediation

We can overwrite the artifact’s version by explicitly defining the artifact in our project’s pom with the desired version
If the same artifact is defined with different versions in 2 imported BOMs, then the version in the BOM file that was declared first will win

## Transitive Dependencies
Maven can discover the libraries that are needed by our own dependencies in our pom.xml and includes them automatically. There’s no limit to the number of dependency levels that the libraries are gathered from.

The conflict here comes when 2 dependencies refer to different versions of a specific artifact. Which one will be included by Maven?

The answer here is the “nearest definition”. This means that the version used will be the closest one to our project in the tree of dependencies. This is called dependency mediation.

Let’s see the following example to clarify the dependency mediation:

```text
A -> B -> C -> D 1.4
A ------> E -> D 1.0
```

- Project A depends on B and E. 
- B and E have their own dependencies which encounter different versions of the D artifact. 
- Artifact D 1.0 will be used in the build of A project because the path through E is shorter.

There are different techniques to determine which version of the artifacts should be included:

We can always guarantee a version by declaring it explicitly in our project’s POM. 
For instance, to guarantee that D 1.4 is used, we should add it explicitly as a dependency in the pom.xml file.

We can use the Dependency Management section to control artifact versions as we will explain later in this article.

## Resources
- [Using Spring Boot without the Parent POM](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-build-systems.html#using-boot-maven-without-a-parent)
- [Spring with Maven Bill Of Materials (BOM)](https://www.baeldung.com/spring-maven-bom)
