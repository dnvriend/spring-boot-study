package com.github.dnvriend;

import com.github.dnvriend.properties.UserProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

// <2 seconds
@SpringJUnitConfig
@TestPropertySource(properties = {
        "com.github.dnvriend.name=dnvriend",
        "com.github.dnvriend.age=42"
})
class UsingSpringExtensionTest {
    @Configuration
    @EnableConfigurationProperties(UserProperties.class)
    static class TestConfig {
    }

    @Autowired
    UserProperties props;

    @Test
    void testGetProperties() {
        assertThat(props.getName()).isEqualTo("dnvriend");
        assertThat(props.getAge()).isEqualTo(42);
    }
}
