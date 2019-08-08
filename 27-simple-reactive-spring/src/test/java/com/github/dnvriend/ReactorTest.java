package com.github.dnvriend;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.time.Duration;

class ReactorTest {
    // A Flux[T] can be thought of as a Source[T]
    // Create a flux (source) that consists of a filter and map operation. The flux emits
    // filtered and uppercase elements.
    private Flux<String> source = Flux.just("John", "Monica", "Mark", "Cloe", "Frank", "Casper", "Olivia", "Emily", "Cate")
            .filter(name -> name.length() == 4)
            .map(String::toUpperCase);

    @Test
    void testAssertSource() {
        // a step verifier takes a publisher, like the flux,
        // and we can assert the elements it emits.
        StepVerifier
                .create(source)
                .expectNext("JOHN")
                .expectNextMatches(name -> name.startsWith("MA"))
                .expectNext("CLOE", "CATE")
                .expectComplete()
                .verify();
    }

    private Flux<String> errorSource = source.concatWith(
            Mono.error(new IllegalArgumentException("test message"))
    );

    @Test
    void testAssertErrorSource() {
        StepVerifier
                .create(errorSource)
                .expectNextCount(4)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals("test message"))
                .verify();
    }

    @Test
    void testLongRunningSource() {
        // the step verfifier takes a publisher, which it loads lazily (the lambda)
        // the publisher is created inline, but that is not necessary
        // the flux, when run in a normal setting will publish a message every second
        // but in the withVirtualTime, the test will not run for 2 seconds, because
        // the time scheduler is under control of the step verifier.
        // instead, time is virtual, and we can reason about the assertion of time
        // in effect this means that the test will run immediate, and all durations are virtual.
        StepVerifier
                .withVirtualTime(() -> Flux.interval(Duration.ofSeconds(1)).take(2))
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .expectNext(0L)
                .thenAwait(Duration.ofSeconds(1))
                .expectNext(1L) .verifyComplete();
    }

    private Flux<Integer> dropElementSource = Flux.<Integer>create(emitter -> {
        emitter.next(1);
        emitter.next(2);
        emitter.next(3);
        emitter.complete();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ie) {
            ie.printStackTrace();
        }
        emitter.next(4);
    }).filter(number -> number % 2 == 0);

    @Test
    void testDroppedElements() {
        StepVerifier
                .create(dropElementSource)
                .expectNext(2)
                .expectComplete()
                .verifyThenAssertThat()
                .hasDropped(4)
                .tookLessThan(Duration.ofMillis(1050));
    }

    static Flux<String> mapToUpperCase(Flux<String> source) {
        return source.map(String::toUpperCase);
    }

    @Test
    void testMapToUpperCase() {
        TestPublisher<String> testPublisher = TestPublisher.create();
        Flux<String> mapToUpperCaseFlux = mapToUpperCase(testPublisher.flux());

        StepVerifier
                .create(mapToUpperCaseFlux)
                .then(() -> testPublisher.emit("aA", "bb", "ccc"))
                .expectNext("AA", "BB", "CCC")
                .verifyComplete();
    }

    @Test
    void testMisbehavingPublisher() {
        // In addition to ALLOW_NULL, we can also use TestPublisher.Violation to:
        // - REQUEST_OVERFLOW – allows calling next() without throwing an IllegalStateException when there’s an insufficient number of requests
        // - CLEANUP_ON_TERMINATE – allows sending any termination signal several times in a row
        // - DEFER_CANCELLATION – allows us to ignore cancellation signals and continue with emitting elements

        TestPublisher misBehaving = TestPublisher.createNoncompliant(TestPublisher.Violation.ALLOW_NULL);

        Flux<String> mapToUpperCaseFlux = mapToUpperCase(misBehaving.flux());

        StepVerifier
                .create(mapToUpperCaseFlux)
                .then(() -> misBehaving.emit("aA", "bb", null, "ccc"))
                .expectNext("AA", "BB")
                .expectError(NullPointerException.class)
                .verify();
    }
}
