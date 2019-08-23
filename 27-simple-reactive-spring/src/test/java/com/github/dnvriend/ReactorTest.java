package com.github.dnvriend;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

class ReactorTest {

    // A Flux[T] can be thought of as a Source[T]
    // Create a flux (source) that consists of a filter and map operation. The flux emits
    // filtered and uppercase elements.
    private Flux<String> source = Flux
        .just("John", "Monica", "Mark", "Cloe", "Frank", "Casper", "Olivia", "Emily", "Cate")
        .filter(name -> name.length() == 4)
        .map(String::toUpperCase);
    private Flux<String> errorSource = source.concatWith(
        Mono.error(new IllegalArgumentException("test message"))
    );
    private Flux<Integer> dropElementSource = Flux.<Integer>create(emitter -> {
        emitter.next(1);
        emitter.next(2);
        emitter.next(3);
        emitter.complete();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        emitter.next(4);
    }).filter(number -> number % 2 == 0);

    static Flux<String> mapToUpperCase(Flux<String> source) {
        return source.map(String::toUpperCase);
    }

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
            .expectNext(1L).verifyComplete();
    }

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

        TestPublisher misBehaving = TestPublisher
            .createNoncompliant(TestPublisher.Violation.ALLOW_NULL);

        Flux<String> mapToUpperCaseFlux = mapToUpperCase(misBehaving.flux());

        StepVerifier
            .create(mapToUpperCaseFlux)
            .then(() -> misBehaving.emit("aA", "bb", null, "ccc"))
            .expectNext("AA", "BB")
            .expectError(NullPointerException.class)
            .verify();
    }

    @Test
    void fluxFromIterable() {
        List<String> strings = Arrays.asList("foo", "bar", "foobar");
        Flux<String> stringFlux = Flux.fromIterable(strings);
        StepVerifier
            .create(stringFlux)
            .expectNext("foo", "bar", "foobar")
            .expectComplete()
            .verify();
    }

    @Test
    void fluxFromJust() {
        Flux<String> stringFlux = Flux.just("foo", "bar", "foobar");
        StepVerifier
            .create(stringFlux)
            .expectNext("foo", "bar", "foobar")
            .expectComplete()
            .verify();
    }

    // materialize Flux

    @Test
    void valueFromStream() {
        List<String> block = Flux.fromStream(Stream.of("foo", "bar", "foobar"))
            .collectList()
            .block();
        assertThat(block).contains("foo", "bar", "foobar");
    }

    @Test
    void valueFromFluxOfMonoFromPossibleNull() {
        List<String> block = Flux.just(
            Mono.justOrEmpty("foo"),
            Mono.<String>justOrEmpty(null),
            Mono.justOrEmpty("bar")
        ).flatMap(Function.identity())
            .collectList()
            .block();
        assertThat(block).contains("foo", "bar");
    }

    @Test
    void valueFromFluxOfMono() {
        List<String> block = Flux
            .just(
                Mono.justOrEmpty(Optional.of("foo")),
                Mono.justOrEmpty(Optional.<String>empty()),
                Mono.justOrEmpty(Optional.of("bar"))
            )
            .flatMap(Function.identity())
            .collectList()
            .block();
        assertThat(block).contains("foo", "bar");
    }

    @Test
    void valueFromFlux() {
        List<String> stringList = Flux.just("foo", "bar", "foobar")
            .collectList()
            .block();
        assertThat(stringList).contains("foo", "bar", "foobar");
    }

    @Test
    void valueFromFluxToIterable() {
        Iterable<Integer> listOfIntegers = Flux.just(1, 2).toIterable();
        assertThat(listOfIntegers).containsSequence(1, 2);
    }

    @Test
    void monoFromJust() {
        Mono<String> stringMono = Mono.just("foo");
        StepVerifier
            .create(stringMono)
            .expectNext("foo")
            .expectComplete()
            .verify();
    }

    @Test
    void monoFromOptional() {
        Mono<String> stringMono = Mono.justOrEmpty(Optional.of("foo"));
        StepVerifier
            .create(stringMono)
            .expectNext("foo")
            .expectComplete()
            .verify();
    }

    // materialize

    @Test
    void valueFromMono() {
        Mono<String> stringMono = Mono.justOrEmpty(Optional.of("foo"));
        String actual = stringMono.block();
        assertThat(actual).isEqualTo("foo");
    }

    @Test
    void subscribeButNothingMaterializesOrReactingToEvents() {
        Flux.range(1, 3).subscribe();
    }

    @Test
    void subscribeAndConsumeElements() {
        Flux.range(1, 3)
            .subscribe(i -> System.out.println(i));
    }

    @Test
    void subscribeAndSubscribeToElementsAndErrors() {
        Flux.range(1, 4)
            .map(i -> {
                if(i <= 3) {
                    return i;
                }
                return new RuntimeException("Got to 4");
            })
            .subscribe(i -> System.out.println(i), err -> System.out.println("Error " + err));
    }

    @Test
    void subscribeAndSubscribeToElementsAndErrorsAndComplete() {
        Flux.range(1, 3)
            .subscribe(i -> System.out.println(i), err -> System.out.println("Error " + err), () -> System.out.println("done"));
    }

    @Test
    void zipFlux() {
        Flux<String> just = Flux.just("foo", "bar");
        Flux<String> just1 = Flux.just("quz", "quux");
        List<Tuple2<String, String>> block = just.zipWith(just1).collectList().block();
        assertThat(block).containsSequence(Tuples.of("foo", "quz"), Tuples.of("bar", "quux"));
    }

    @Test
    void concatFlux() {
        Flux<String> just = Flux.just("foo", "bar");
        Flux<String> just1 = Flux.just("quz", "quux");
        List<String> block = just.concatWith(just1).collectList().block();
        assertThat(block).containsSequence("foo", "bar", "quz", "quux");
    }

    /*
     * When you have lots of elements and you want to separate them into batches, you have three
     * broad solutions in Reactor: grouping, windowing, and buffering. These three are conceptually
     * close, because they redistribute a Flux<T> into an aggregate. Grouping and windowing create
     * a Flux<Flux<T>>, while buffering aggregates into a Collection<T>
     */

    /*
     * see: https://projectreactor.io/docs/core/release/reference/#_grouping_with_fluxgroupedfluxt
     *
     * Grouping is the act of splitting the source Flux<T> into multiple batches by a key.
     * Each group is represented as a GroupedFlux<T>, which lets you retrieve the key via its key() method.
     * There is no necessary continuity in the content of the groups. Once a source element produces
     * a new key, the group for this key is opened and elements that match the key end up in the group
     * (several groups could be open at the same time).
     *
     * This means that groups:
     * 1. Are always disjoint (a source element belongs to 1 and only 1 group).
     * 2. Can contain elements from different places in the original sequence.
     * 3. Are never empty.
     *
     * Grouping is best suited for when you have a medium to low number of groups. The groups
     * must also imperatively be consumed (such as by a flatMap) so that groupBy continues fetching
     * data from upstream and feeding more groups. Sometimes, these two constraints multiply and
     * lead to hangs, such as when you have a high cardinality and the concurrency of the flatMap
     * consuming the groups is too low.
     */
    @Test
    void fluxGroupBy() {
        StepVerifier.create(
            Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .groupBy(i -> i % 2 == 0 ? "even" : "odd")
                .concatMap(g -> g.defaultIfEmpty(-1) //if empty groups, show them
                    .map(String::valueOf) //map to string
                    .startWith(g.key())) //start with the group's key
        )
            .expectNext("odd", "1", "3", "5", "11", "13")
            .expectNext("even", "2", "4", "6", "12")
            .verifyComplete();
    }

    /*
     * see: https://projectreactor.io/docs/core/release/reference/#_windowing_with_fluxfluxt
     *
     * Windowing is the act of splitting the source Flux<T> into windows, by criteria of size,
     * time, boundary-defining predicates, or boundary-defining Publisher.
     *
     * The associated operators are window, windowTimeout, windowUntil, windowWhile, and windowWhen.
     *
     * Contrary to groupBy, which randomly overlaps according to incoming keys, most of the time
     * windows are opened sequentially.
     *
     * Some variants can still overlap, though. For instance in window(int maxSize, int skip)
     * the maxSize parameter is the number of elements after which a window closes, and the skip
     * parameter is the number of elements in the source after which a new window is opened.
     * So if maxSize > skip, a new window opens before the previous one closes and the two windows
     * overlap.
     *
     * With the reverse configuration (maxSize < skip), some elements from the source are dropped
     * and are not part of any window.
     */
    @Test
    void fluxOverlappingWindow() {
        StepVerifier.create(
            Flux.range(1, 10)
                .window(5, 3) //overlapping windows
                .concatMap(g -> g.defaultIfEmpty(-1)) //show empty windows as -1
            )
            .expectNext(1, 2, 3, 4, 5) // 1, 2, 3 -> new window opens, 4, 5 -> window closes
            .expectNext(4, 5, 6, 7, 8) // 4, 5, 6 -> new window opens, 7, 8 -> window closes
            .expectNext(7, 8, 9, 10) // 7, 8, 9 -> new window opens, 10 -> window closes
            .expectNext(10) // 10 -> window closes
            .verifyComplete();
    }

    @Test
    void fluxNonOverlappingWindow() {
        StepVerifier.create(
            Flux.range(1, 10)
                .window(5, 5) //overlapping windows
                .concatMap(g -> g.defaultIfEmpty(-1)) //show empty windows as -1
            )
            .expectNext(1, 2, 3, 4, 5)
            .expectNext(6, 7, 8, 9, 10)
            .verifyComplete();
    }

    @Test
    void fluxDroppingElementsWindow() {
        StepVerifier.create(
            Flux.range(1, 10)
                .window(4, 5) //overlapping windows
                .concatMap(g -> g.defaultIfEmpty(-1)) //show empty windows as -1
        )
            .expectNext(1, 2, 3, 4) // 1, 2, 3, 4 -> close window -> drop 5 -> new window
            .expectNext(6, 7, 8, 9) // 6, 7, 8, 9 -> close window -> drop 10
            .verifyComplete();
    }

    @Test
    void fluxPredicateWindow() {
        StepVerifier.create(
            Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .windowWhile(i -> i % 2 == 0)
                .concatMap(g -> g.defaultIfEmpty(-1))
            )
            .expectNext(-1, -1, -1) //respectively triggered by odd 1 3 5
            .expectNext(2, 4, 6) // triggered by 11
            .expectNext(12) // triggered by 13
            // however, no empty completion window is emitted (would contain extra matching elements)
            .verifyComplete();
    }

    @Test
    void fluxParallelismOnDefaultScheduler() {
        Flux.range(1, 10)
            .parallel(2)
            .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
    }

    @Test
    void fluxParallelismOnParallelScheduler() {
        Flux.range(1, 10)
            .parallel(2)
            .runOn(Schedulers.parallel())
            .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
    }
}
