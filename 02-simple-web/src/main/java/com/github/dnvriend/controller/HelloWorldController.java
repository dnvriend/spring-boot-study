package com.github.dnvriend.controller;

import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(value = "HelloWorld resource", description = "operations related to HelloWorld")
@RestController()
@RequestMapping("/hello")
public class HelloWorldController {

    @ApiOperation(value = "Get HelloWorld message")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success Message")
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    // when '/' is removed, it will handle both 'hello' and 'hello/', else it will handle only 'hello/'
    public Map<String, String> sayHello() {
        return ImmutableMap.of("message", "Hello World");
    }
}
