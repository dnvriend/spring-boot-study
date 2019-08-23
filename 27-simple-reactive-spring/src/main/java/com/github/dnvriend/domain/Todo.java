package com.github.dnvriend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Todo {
    private Long id;
    private int userId;
    private String title;
    private boolean completed;
}
