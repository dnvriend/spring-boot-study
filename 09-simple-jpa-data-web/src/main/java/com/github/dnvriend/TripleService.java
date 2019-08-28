package com.github.dnvriend;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class TripleService {

    @Autowired
    private TripleRepository tripleRepository;

    @Autowired
    private EntityManager em;

    public <A> Stream<A> find(String queryString, Function<Object[], A> mapper) {
        // must be an @Entity and entities must have an @Id
//        Query query = em.createNativeQuery("select k1 from triple", FirstKey.class);
        // if you request 1 field, then you get a String
        // if you request multiple fields, then you get an array
        Query query = em.createNativeQuery(queryString);
        return query.getResultStream().map(e -> mapper.apply((Object[]) e));
    }

    public Triple saveTriple(String k1, String k2, String k3, String value) {
        return tripleRepository.save(
            Triple.builder()
                .id(TripleKeyId.builder()
                    .k1(k1)
                    .k2(k2)
                    .k3(k3)
                    .build())
                .value(value)
                .build());
    }

    public Triple saveTripleWithInterval(String k1, String k2, String k3, DateTime start,
        DateTime end, String value) {
        return tripleRepository.save(
            Triple.builder()
                .id(TripleKeyId.builder()
                    .k1(k1)
                    .k2(k2)
                    .k3(k3)
                    .build())
                .start(start)
                .end(end)
                .value(value)
                .build());
    }

    public boolean exists(String k1, String k2, String k3) {
        return tripleRepository.exists(Example.of(
            Triple.builder()
                .id(TripleKeyId.builder()
                    .k1(k1)
                    .k2(k2)
                    .k3(k3)
                    .build())
                .build())
        );
    }

    public boolean exists(String k1, String k2, String k3, DateTime start, DateTime end) {
        return tripleRepository.exists(Example.of(
            Triple.builder()
                .id(TripleKeyId.builder()
                    .k1(k1)
                    .k2(k2)
                    .k3(k3)
                    .build())
                .start(start)
                .end(end)
                .build())
        );
    }

    public Optional<Triple> findOne(String k1, String k2, String k3) {
        return tripleRepository.findOne(Example.of(
            Triple.builder()
                .id(TripleKeyId.builder()
                    .k1(k1)
                    .k2(k2)
                    .k3(k3)
                    .build())
                .build())
        );
    }

    public Optional<Triple> findOne(String k1, String k2, String k3, DateTime start, DateTime end) {
        return tripleRepository.findOne(Example.of(
            Triple.builder()
                .id(TripleKeyId.builder()
                    .k1(k1)
                    .k2(k2)
                    .k3(k3)
                    .build())
                .start(start)
                .end(end)
                .build())
        );
    }

    public Stream<Triple> findById(String k1, String k2, String k3) {
        return tripleRepository.findById_K1AndId_K2AndId_K3(k1, k2, k3);
    }
}