package com.github.dnvriend;

import com.github.dnvriend.converters.DateTimeAttributeConverter;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@SqlResultSetMapping(
    name="FirstKeyResult",
    classes={
        @ConstructorResult(
            targetClass=FirstKey.class,
            columns={
                @ColumnResult(name="id_k1", type=String.class)}
        )
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "triple")
public class Triple {

    @EmbeddedId
    private TripleKeyId id;
    private String value;
    @Convert(converter = DateTimeAttributeConverter.class)
    private DateTime start;
    @Convert(converter = DateTimeAttributeConverter.class)
    private DateTime end;
}