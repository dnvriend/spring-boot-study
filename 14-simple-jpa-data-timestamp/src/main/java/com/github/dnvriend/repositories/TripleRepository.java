package com.github.dnvriend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface TripleRepository extends JpaRepository<Triple, TripleKeyId> {
    Stream<Triple> findById_K1AndId_K2AndId_K3(String k1, String k2, String k3);
    Stream<Triple> findById_K1AndId_K2AndId_K3OrderById_StartAsc(String k1, String k2, String k3);
    Stream<Triple> findById_K1AndId_K2AndId_K3OrderById_StartDesc(String k1, String k2, String k3);
}
