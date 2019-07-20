package com.github.dnvriend.controllers;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Person", description = "A person")
public class Person {

    @ApiModelProperty(notes = "The id of the person", hidden = true)
    int id;

    @NotNull
    @Size(min = 0, max = 255)
    @ApiModelProperty(notes = "The name of the person", required = true, dataType = "string", example = "MrBean")
    String name;

    @Min(0)
    @Max(110)
    @ApiModelProperty(notes = "The age of the person", required = true, dataType = "int", example = "42")
    int age;
}
