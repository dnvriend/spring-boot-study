package com.github.dnvriend.filters;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Tuple {

    String _1;
    Collection<String> _2;
}
