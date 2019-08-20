package com.github.dnvriend;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    void say(@NonNull String msg) {
        System.out.println(msg);
    }
}
