package com.github.dnvriend.service;

import com.github.dnvriend.repository.Triple;
import com.github.dnvriend.repository.TripleKeyId;
import com.github.dnvriend.repository.TripleRepository;
import java.util.Optional;
import java.util.stream.Stream;
import org.joda.time.DateTime;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class TripleService {

    private final TripleRepository tripleRepository;

    public TripleService(TripleRepository tripleRepository) {
        this.tripleRepository = tripleRepository;
    }

    public Triple saveTripleWithInterval(String k1, String k2, String k3, DateTime start,
        DateTime end, String value) {
        return tripleRepository.save(
            Triple.builder()
                .id(TripleKeyId.builder()
                    .k1(k1)
                    .k2(k2)
                    .k3(k3)
                    .start(start)
                    .end(end)
                    .build())
                .value(value)
                .build());
    }

    public boolean exists(String k1, String k2, String k3, DateTime start, DateTime end) {
        return tripleRepository.exists(Example.of(
            Triple.builder()
                .id(TripleKeyId.builder()
                    .k1(k1)
                    .k2(k2)
                    .k3(k3)
                    .start(start)
                    .end(end)
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
                    .start(start)
                    .end(end)
                    .build())
                .build())
        );
    }

    public Stream<Triple> findById(String k1, String k2, String k3) {
        return tripleRepository.findById_K1AndId_K2AndId_K3(k1, k2, k3);
    }

    public Stream<Triple> findByIdAsc(String k1, String k2, String k3) {
        return tripleRepository.findById_K1AndId_K2AndId_K3OrderById_StartAsc(k1, k2, k3);
    }

    public Stream<Triple> findByIdDesc(String k1, String k2, String k3) {
        return tripleRepository.findById_K1AndId_K2AndId_K3OrderById_StartDesc(k1, k2, k3);
    }
}