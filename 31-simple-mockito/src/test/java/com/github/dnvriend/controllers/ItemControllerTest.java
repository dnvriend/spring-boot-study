package com.github.dnvriend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ItemControllerTest.class})
public class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testJsonAssertion() throws Exception {
        mockMvc.perform(get("/items/1").accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Ball")))
                .andExpect(jsonPath("$.price", is(10)))
                .andExpect(jsonPath("$.quantity", is(100)));
    }


    @Configuration
    @ComponentScan("com.github.dnvriend.controllers")
    static class TestConfiguration {

    }
}
