package com.github.dnvriend.service;

import com.github.dnvriend.annotation.LogExecutionTime;
import com.github.dnvriend.annotation.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyService {

    @LogExecutionTime
    @LogMessage("Hello World!")
    public void say(@NonNull String msg) {
        log.debug(msg);
    }
}
