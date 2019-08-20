package com.github.dnvriend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MockWithFixtureTest {

    private Fixture fixture;

    @BeforeEach
    void setUp() {
        this.fixture = new Fixture();
    }

    @Test
    void test() {
        assertThat(fixture.mockedList).isNotNull();
    }

    static class Fixture {

        // put mocks here

        @Mock
        List<String> mockedList;

        public Fixture() {
            MockitoAnnotations.initMocks(this);
            // after '.initMocks()', you can configure the mocks
        }

        // put methods here that configure mocks in a certain way
        // these methods configure the mocks and return void
    }
}
