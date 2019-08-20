package com.github.dnvriend.repositories;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
