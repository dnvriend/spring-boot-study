package com.github.dnvriend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
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
}
