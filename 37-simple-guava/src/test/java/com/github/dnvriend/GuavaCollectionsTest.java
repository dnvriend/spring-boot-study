package com.github.dnvriend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Streams;
import org.junit.jupiter.api.Test;

class GuavaCollectionsTest {

    @Test
    void createMap() {
        Map<String, String> map = ImmutableMap.<String, String>builder()
            .put("foo", "bar")
            .putAll(ImmutableMap.<String, String>builder().put("quz", "quux").build())
            .build();

        assertThat(map).contains(entry("foo", "bar"));
    }

    @Test
    void createList() {
        List<String> xs = ImmutableList.<String>builder().add("foo", "bar").build();
        assertThat(xs).contains("foo", "bar");
    }

    void mergeMaps() {
        Map<String, Map<String, List<Integer>>> kv1 = ImmutableMap.of("foo", ImmutableMap.of("bar", ImmutableList.of(1)));
        Map<String, Map<String, List<Integer>>> kv2 = ImmutableMap.of("foo", ImmutableMap.of("bar", ImmutableList.of(2)));

        ImmutableList<String> foo = ImmutableList.of("foo");

        Streams.


    }
}
