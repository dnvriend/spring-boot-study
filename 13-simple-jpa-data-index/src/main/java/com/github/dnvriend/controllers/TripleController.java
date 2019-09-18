package com.github.dnvriend.controller;

import com.github.dnvriend.service.DateTimeIntervalValidator;
import com.github.dnvriend.service.TripleService;
import java.util.List;
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TripleController {

    private final TripleService tripleService;

    private final ConversionService conversionService;

    private final DateTimeIntervalValidator dateTimeIntervalValidator;

    public TripleController(TripleService tripleService, ConversionService conversionService,
        DateTimeIntervalValidator dateTimeIntervalValidator) {
        this.tripleService = tripleService;
        this.conversionService = conversionService;
        this.dateTimeIntervalValidator = dateTimeIntervalValidator;
    }

    @PutMapping("/{k1}/{k2}/{k3}/{start}/{end}")
    public void putTripleWithInterval(
        @PathVariable("k1") @NonNull String k1,
        @PathVariable("k2") @NonNull String k2,
        @PathVariable("k3") @NonNull String k3,
        @PathVariable("start") @NonNull DateTime start,
        @PathVariable("end") @NonNull DateTime end,
        @RequestBody @NonNull Value value) {
        dateTimeIntervalValidator.validate(start, end);
        if (tripleService.exists(k1, k2, k3, start, end)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Resource already exists");
        }
        tripleService.saveTripleWithInterval(k1, k2, k3, start, end, value.getValue());
    }

    @Transactional(readOnly = true)
    @RequestMapping(path = "/{k1}/{k2}/{k3}/{start}/{end}", method = RequestMethod.HEAD)
    public void checkTripleByInterval(
        @PathVariable("k1") @NonNull String k1,
        @PathVariable("k2") @NonNull String k2,
        @PathVariable("k3") @NonNull String k3,
        @PathVariable("start") @NonNull DateTime start,
        @PathVariable("end") @NonNull DateTime end) {
        dateTimeIntervalValidator.validate(start, end);
        tripleService.findOne(k1, k2, k3, start, end)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Triple not found: %s/%s/%s/%s/%s", k1, k2, k3, start, end)));
    }

    @Transactional(readOnly = true)
    @GetMapping("/{k1}/{k2}/{k3}/{start}/{end}")
    public List<Value> getTriplesWithInterval(
        @PathVariable("k1") @NonNull String k1,
        @PathVariable("k2") @NonNull String k2,
        @PathVariable("k3") @NonNull String k3,
        @PathVariable("start") @NonNull DateTime start,
        @PathVariable("end") @NonNull DateTime end) {
        dateTimeIntervalValidator.validate(start, end);
        Interval interval = new Interval(start, end);
        return tripleService.findById(k1, k2, k3)
            .filter(tripleWithInterval -> {
                Interval jpaInterval = new Interval(tripleWithInterval.getId().getStart(),
                    tripleWithInterval.getId().getEnd());
                return jpaInterval.overlaps(interval);
            })
            .map(triple -> conversionService.convert(triple, Value.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @GetMapping("/{k1}/{k2}/{k3}/asc")
    public List<Value> getTriplesByIdAsc(
        @PathVariable("k1") @NonNull String k1,
        @PathVariable("k2") @NonNull String k2,
        @PathVariable("k3") @NonNull String k3) {
        return tripleService.findByIdAsc(k1, k2, k3)
            .map(triple -> conversionService.convert(triple, Value.class))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @GetMapping("/{k1}/{k2}/{k3}/desc")
    public List<Value> getTriplesByIdDesc(
        @PathVariable("k1") @NonNull String k1,
        @PathVariable("k2") @NonNull String k2,
        @PathVariable("k3") @NonNull String k3) {
        return tripleService.findByIdDesc(k1, k2, k3)
            .map(triple -> conversionService.convert(triple, Value.class))
            .collect(Collectors.toList());
    }
}