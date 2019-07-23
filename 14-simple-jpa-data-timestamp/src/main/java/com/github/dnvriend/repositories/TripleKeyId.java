package com.github.dnvriend.repositories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class TripleKeyId implements Serializable {
    private String k1;
    private String k2;
    private String k3;
    private LocalDateTime start;
    private LocalDateTime end;
}
