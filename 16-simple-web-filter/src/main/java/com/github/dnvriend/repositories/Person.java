package com.github.dnvriend.repositories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Person {

    private final int id;
    private final String name;
    private final int age;
}
