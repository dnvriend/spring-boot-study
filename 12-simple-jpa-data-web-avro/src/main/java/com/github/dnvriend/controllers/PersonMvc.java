package com.github.dnvriend.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonMvc {
    private Long id;
    private Optional<String> name = Optional.empty();
    private Optional<Integer> age = Optional.empty();
}
