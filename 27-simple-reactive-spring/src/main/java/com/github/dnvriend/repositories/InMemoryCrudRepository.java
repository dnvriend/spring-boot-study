package com.github.dnvriend.repository;

import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.relational.repository.query.RelationalEntityInformation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class InMemoryCrudRepository<T, ID> implements ReactiveCrudRepository<T, ID> {
    private Map<ID, T> map = new HashMap<>();

    private final @NonNull RelationalEntityInformation<T, ID> info;

    @Override
    public <S extends T> Mono<S> save(S entity) {
        map.put(info.getId(entity), entity);
        return Mono.just(entity);
    }

    @Override
    public <S extends T> Flux<S> saveAll(Iterable<S> entities) {
        return Flux.zip(Flux.fromIterable(entities).map(info::getId),
            Flux.fromIterable(entities)).map( kv -> {
                map.put(kv.getT1(), kv.getT2());
                return kv.getT2();
        });
    }

    @Override
    public <S extends T> Flux<S> saveAll(Publisher<S> entityStream) {
        return saveAll(Flux.from(entityStream).toIterable());
    }

    @Override
    public Mono<T> findById(ID id) {
        return findAll().filter(e -> info.getId(e).equals(id)).next();
    }

    @Override
    public Mono<T> findById(Publisher<ID> id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(ID id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<ID> id) {
        return null;
    }

    @Override
    public Flux<T> findAll() {
        return Flux.fromIterable(map.values());
    }

    @Override
    public Flux<T> findAllById(Iterable<ID> ids) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Flux<T> findAllById(Publisher<ID> idStream) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Mono<Long> count() {
        return Mono.just((long) map.size());
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Mono<Void> deleteById(Publisher<ID> id) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Mono<Void> delete(T entity) {
        map.remove(entity);
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends T> entities) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends T> entityStream) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Mono<Void> deleteAll() {
        map.clear();
        return Mono.empty();
    }
}
