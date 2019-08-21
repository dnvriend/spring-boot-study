# 32-simple-javadoc

## Some tips

- The code explains how, the doc explains why,
- Classes inherits all the comments for methods which are either an implementation over other methods or is overridden, 

## TL;DR
Create good documentation:

- by keeping documentation simple and concise.
- by keeping documentation close to the code and close to the API, which are the ultimate truths of your application.
- by keeping your documentation DRY.
- by making documentation available to others, through a ticketing system, version control, semantic versioning.
- by referencing ticket IDs throughout your available media.
- by forgetting about “external” documentation, as long as you can.

Applications, APIs, libraries that provide you with good documentation will help you create better software, because well-documented applications, APIs, libraries are better software, themselves. Critically check your stack and try to avoid those parts that are not well-documented. 

## Comment recap

```
# single line comment
// this is a comment

# multi-line comment
/*
 * This is a comment
 */

# javadoc 
/**
 * This is JavaDoc
 */
```

## JavaDoc tags:

- @param: provides any useful description about a method’s parameter or input it should expect
- @return: provides a description of what a method will or can return
- @see: will generate a link similar to the {@link} tag, but more in the context of a reference and not inline
- @since: specifies which version the class, field, or method was added to the project
- @version: specifies the version of the software, commonly used with %I% and %G% macros
- @throws: is used to further explain the cases the software would expect an exception
- @deprecated: gives an explanation of why code was deprecated, when it may have been deprecated, and what the alternatives are

## JavaDoc example
Class level:

```
/**
* Hero is the main entity we'll be using to . . .
* 
* Please see the {@link com.baeldung.javadoc.Person} class for true identity
* @author Captain America
* 
*/
public class SuperHero extends Person {
    // fields and methods
}
```

- Standalone tags appear after the description with the tag as the first word in a line, e.g., the @author tag
- Inline tags may appear anywhere and are surrounded with curly brackets, e.g., the @link tag in the description

Another example:

```
/**
 * Helper class that encapsulates the specification of a method parameter, i.e. a {@link Method}
 * or {@link Constructor} plus a parameter index and a nested type index for a declared generic
 * type. Useful as a specification object to pass along.
 *
 * <p>As of 4.2, there is a {@link org.springframework.core.annotation.SynthesizingMethodParameter}
 * subclass available which synthesizes annotations with attribute aliases. That subclass is used
 * for web and message endpoint processing, in particular.
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Andy Clement
 * @author Sam Brannen
 * @author Sebastien Deleuze
 * @since 2.0
 * @see org.springframework.core.annotation.SynthesizingMethodParameter
 */
```

Method level:
```
/**
 * <p>This is a simple description of the method. . .
 * <a href="http://www.supermanisthegreatest.com">Superman!</a>
 * </p>
 * @param incomingDamage the amount of incoming damage
 * @return the amount of health hero has after attack
 * @see <a href="http://www.link_to_jira/HERO-402">HERO-402</a>
 * @since 1.0
 */
public int successfullyAttacked(int incomingDamage) {
    // do things
    return 0;
}
```

Another example:

```
/**
 * Create a new {@code MethodParameter} for the given method, with nesting level 1.
 * @param method the Method to specify a parameter for
 * @param parameterIndex the index of the parameter: -1 for the method
 * return type; 0 for the first method parameter; 1 for the second method
 * parameter, etc.
 */
```

## Maven Plugin

```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>3.1.1</version>
</plugin>
```

## Maven goal

```
mvn javadoc:javadoc
```

## Resources
- [Baeldung - JavaDoc](https://www.baeldung.com/javadoc)
- [JavaDoc best practice](https://dzone.com/articles/best-practices-of-code-documentation-in-java)
- [The golden rules of code documentation](https://blog.jooq.org/2013/02/26/the-golden-rules-of-code-documentation/)
- [How to write doc comments for the javadoc tool](https://www.oracle.com/technetwork/java/javase/documentation/index-137868.html#styleguide)
