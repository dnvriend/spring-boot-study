package com.github.dnvriend.controller;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Value {

    private String value;
    private LocalDateTime start;
    private LocalDateTime end;
}
