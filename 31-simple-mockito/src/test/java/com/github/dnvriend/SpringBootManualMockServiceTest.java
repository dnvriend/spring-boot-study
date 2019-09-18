package com.github.dnvriend;

import com.github.dnvriend.service.NameService;
import com.github.dnvriend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * In this test we create the test configuration and wire up the test manually
 */
@SpringBootTest
class SpringBootManualMockServiceTest {

    @Autowired
    NameService nameService;
    @Autowired
    UserService userService;

    @Test
    void testService() {
        Mockito.when(nameService.getUserName("someId")).thenReturn("Mock user name");
        String actual = nameService.getUserName("someId");
        assertThat(actual).isEqualTo("Mock user name");
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public NameService nameService() {
            return Mockito.mock(NameService.class);
        }

        @Bean
        public UserService userService(NameService nameService) {
            return new UserService(nameService);
        }
    }
}
