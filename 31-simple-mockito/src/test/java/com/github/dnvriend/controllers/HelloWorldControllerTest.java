package com.github.dnvriend.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest({HelloWorldController.class})
class HelloWorldControllerTest {

    // spring test provides MockMvc
    @Autowired
    MockMvc mockMvc;

    @Test
    void testControllerRequestBuilder() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
            .get("/hello-world")
            .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request).andDo(print()).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Hello World");
    }

    @Test
    void testControllerMatcher() throws Exception {
        mockMvc.perform(get("/hello-world"))
            .andDo(print())
            // andExpect(matcher)
            .andExpect(status().isOk())
//            .andExpect(content().string(containsString("Hello World")))
            .andExpect(content().string("Hello World"))
            .andReturn();
    }


    @Configuration
    @ComponentScan("com.github.dnvriend.controllers")
    static class TestConfiguration {

    }
}
