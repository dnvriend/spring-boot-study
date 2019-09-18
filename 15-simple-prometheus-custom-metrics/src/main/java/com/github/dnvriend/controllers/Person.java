package com.github.dnvriend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {

    private final int id;
    private final String name;
    private final int age;
}
