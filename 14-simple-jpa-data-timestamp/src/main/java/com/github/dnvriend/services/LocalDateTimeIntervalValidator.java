package com.github.dnvriend.service;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class LocalDateTimeIntervalValidator {

    public void validate(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "end is before start");
        }
    }
}
