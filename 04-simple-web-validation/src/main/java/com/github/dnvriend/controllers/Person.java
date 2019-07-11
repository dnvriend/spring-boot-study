package com.github.dnvriend.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    @Min(value = 0, message = "id should be larger than 0")
    private int id;

    @NotEmpty(message = "name should not be null")
    @Size(min = 4, max = 30, message = "name should be between 4 and 30 chars")
    private String name;

    @Min(value = 0, message = "age should not be less than 0")
    @Max(value = 100, message = "age should not be greater than 100")
    private int age;

    @NotNull(message = "title should not be null")
    private String title;

    @AssertTrue(message = "should be working")
    private boolean working;

    @NotNull(message = "Email should not be null")
    @Email(message = "Email should be valid")
    private String email;
}
