package com.github.dnvriend.repositories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "triple")
@Table(indexes = {
        @Index(name = "KEYS_INDEX", columnList = "id_k1, id_k2, id_k3"),
        @Index(name = "KEYS_INDEX_START_ASC", columnList = "id_k1, id_k2, id_k3, id_start ASC"),
        @Index(name = "KEYS_INDEX_START_DESC", columnList = "id_k1, id_k2, id_k3, id_start DESC")
})
public class Triple {
    @EmbeddedId
    private TripleKeyId id;
    private String value;
}