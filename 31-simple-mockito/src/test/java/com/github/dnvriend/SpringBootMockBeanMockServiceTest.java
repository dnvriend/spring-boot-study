package com.github.dnvriend;

import com.github.dnvriend.service.NameService;
import com.github.dnvriend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * In this test we create the test configuration and wire up the test manually
 */
@SpringBootTest
class SpringBootMockBeanMockServiceTest {

    // The ComponentScan detected NameService will be replaced by the mock
    // and injected in the field.
    @MockBean
    NameService nameService;
    // Mocked NameService will also be injected in the UserService
    @Autowired
    UserService userService;

    @Test
    void testService() {
        Mockito.when(nameService.getUserName("someId")).thenReturn("Mock user name");
        String actual = nameService.getUserName("someId");
        assertThat(actual).isEqualTo("Mock user name");
    }

    @Configuration
    @ComponentScan("com.github.dnvriend.services")
    static class TestConfiguration {
        // all services will be added to the application context
    }
}
