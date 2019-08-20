package com.github.dnvriend.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class HelloWorldControllerTest {

    // spring test provides MockMvc
    @Autowired
    MockMvc mockMvc;

    @Test
    void testController() throws Exception {
        mockMvc.perform(get("/hello-world"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello World")));
    }

    @Configuration
    @ComponentScan("com.github.dnvriend.controllers")
    static class TestConfiguration {

    }
}
