package com.github.dnvriend.services;

import com.github.dnvriend.domain.Todo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class TodoService {

    public Flux<Todo> getTodos() {
        return Flux.just(
            Todo.builder().userId(1).title("Title1").completed(false).build(),
            Todo.builder().userId(2).title("Title2").completed(true).build(),
            Todo.builder().userId(3).title("Title3").completed(false).build(),
            Todo.builder().userId(4).title("Title4").completed(true).build(),
            Todo.builder().userId(5).title("Title5").completed(false).build()
        );
    }

    private WebClient client() {
        return WebClient.create("https://jsonplaceholder.typicode.com");
    }

    private Flux<Todo> getTodosFromWS() {
        return client().get()
            .uri("/todos")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Todo.class);
    }
}
