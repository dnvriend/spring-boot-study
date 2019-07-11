package com.github.dnvriend;

import org.springframework.stereotype.Component;

@Component
public class MyBean {
    void say(String msg) {
        System.out.println(msg);
    }
}
