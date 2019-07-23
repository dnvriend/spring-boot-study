package com.github.dnvriend.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeIntervalValidator {
    public void validate(LocalDateTime start, LocalDateTime end) {
        if(end.isBefore(start)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "end is before start");
        }
    }
}
