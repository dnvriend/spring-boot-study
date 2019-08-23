package com.github.dnvriend;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.dnvriend.domain.Post;
import com.github.dnvriend.domain.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * see: https://docs.spring.io/spring-framework/docs/5.0.x/spring-framework-reference/web-reactive.html#webflux-client
 *
 * In contrast to RestTemplate, the WebClient is:
 *
 * - non-blocking, reactive, and supports higher concurrency with less hardware resources
 * - provides a functional API that takes advantage of Java 8 lambdas
 * - supports both asynchronous and asynchronous scenarios
 * - supports streaming up or down from a server
 *
 */
class WebClientTest {
    // For the API see: https://jsonplaceholder.typicode.com/
    WebClient client = WebClient.create("https://jsonplaceholder.typicode.com");

    @Test
    void getTodoMono() {
        Mono<Todo> todoMono = client.get()
            .uri("/todos/{id}", 1)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Todo.class);

        Todo expected = todoMono.block();
        assertThat(expected.getId()).isEqualTo(1);
        assertThat(expected.isCompleted()).isFalse();
        assertThat(expected.getUserId()).isEqualTo(1);
    }

    @Test
    void getPostsFlux() {
        Flux<Post> fluxOfPost = client.get()
            .uri("/posts")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Post.class);

        Iterable<Post> listOfPost = fluxOfPost.toIterable();
        assertThat(listOfPost).isNotEmpty();
    }
}
