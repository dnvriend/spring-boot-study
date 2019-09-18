package com.github.dnvriend.controller;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonMvc {

    private Long id;
    private Optional<String> name = Optional.empty();
    private Optional<Integer> age = Optional.empty();
}
