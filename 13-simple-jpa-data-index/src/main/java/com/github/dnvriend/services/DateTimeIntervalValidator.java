package com.github.dnvriend.services;

import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class DateTimeIntervalValidator {

    public void validate(DateTime start, DateTime end) {
        if (end.isBefore(start)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "end is before start");
        }
    }
}
