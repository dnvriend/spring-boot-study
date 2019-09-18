package com.github.dnvriend;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalTest {

    @Test
    void listOfDefined() {
        List<Integer> xs = Stream.of(
                Optional.of(1), Optional.of(2), Optional.of(3)
        ).flatMap(OptionalTest::asStream)
                .collect(Collectors.toList());
        assertThat(xs).containsSequence(1, 2, 3);
    }

    @Test
    void listWithUndefined() {
        List<Integer> xs = Stream.of(
                Optional.of(1),
                Optional.<Integer>empty(),
                Optional.of(2),
                Optional.<Integer>empty(),
                Optional.of(3)
        ).flatMap(OptionalTest::asStream)
                .collect(Collectors.toList());
        assertThat(xs).containsSequence(1, 2, 3);
    }

    public static <T> Stream<T> asStream(Optional<T> opt) {
        return opt.map(Stream::of).orElseGet(Stream::empty);
    }

    @Test
    void testGettingValueFromMap() {
        ImmutableMap<String, String> map = ImmutableMap.<String, String>builder()
                .put("k1", "v1")
                .put("k2", "v2")
                .put("k3", "v3")
                .put("k4", "v4")
                .put("k5", "v5")
                .build();
        Object v = map.get("foo");
        assertThat(v).isNull();

        Optional<String> opt = Optional.ofNullable(map)
                .map(m -> m.get("foo"));

        Optional.ofNullable(1).ifPresent(System.out::println);


    }

    static String getValue() {
        return null;
    }

    @Test
    void testNullInStream() {
        Stream stream = Stream.of(getValue());
        stream.forEach(System.out::println);
    }

}
