package com.github.dnvriend.repositories;

import com.github.dnvriend.converters.DateTimeAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class TripleKeyId implements Serializable {
    private String k1;
    private String k2;
    private String k3;
    @Convert(converter = DateTimeAttributeConverter.class)
    private DateTime start;
    @Convert(converter = DateTimeAttributeConverter.class)
    private DateTime end;
}
