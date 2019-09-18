package com.github.dnvriend.repository;

import com.github.dnvriend.domain.Todo;
import lombok.NonNull;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.relational.repository.query.RelationalEntityInformation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Note: Query derivation is not yet supported
 */
//public interface TodoRepository extends ReactiveCrudRepository<Todo, Long> {
//    @Query("SELECT * FROM todo WHERE title = :title")
//    Flux<Todo> findAllByTitle(String title);
//
//    @Query("SELECT * FROM todo WHERE completed = :completed")
//    Flux<Todo> findAllByCompleted(boolean completed);
//}

@Component
public class TodoRepository extends InMemoryCrudRepository<Todo, Long> {
    public TodoRepository(
        @NonNull RelationalEntityInformation<Todo, Long> info) {
        super(info);
    }
}