package com.github.dnvriend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // tells us where the annotation is applicable
@Retention(RetentionPolicy.RUNTIME) // whether the annotation is available to the JVM at runtime
public @interface LogExecutionTime {

}
