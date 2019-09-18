package com.github.dnvriend.repository;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TripleRepository extends JpaRepository<Triple, TripleKeyId> {

    Stream<Triple> findById_K1AndId_K2AndId_K3(String k1, String k2, String k3);
}
