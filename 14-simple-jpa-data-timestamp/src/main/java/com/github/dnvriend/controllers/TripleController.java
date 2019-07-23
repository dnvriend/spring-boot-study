package com.github.dnvriend.controllers;

import com.github.dnvriend.services.LocalDateTimeIntervalValidator;
import com.github.dnvriend.services.TripleService;
import org.joda.time.Interval;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TripleController {

    private final TripleService tripleService;

    private final ConversionService conversionService;

    private final LocalDateTimeIntervalValidator localDateTimeIntervalValidator;

    public TripleController(TripleService tripleService, ConversionService conversionService, LocalDateTimeIntervalValidator localDateTimeIntervalValidator) {
        this.tripleService = tripleService;
        this.conversionService = conversionService;
        this.localDateTimeIntervalValidator = localDateTimeIntervalValidator;
    }

    @PutMapping("/{k1}/{k2}/{k3}/{start}/{end}")
    public void putTripleWithInterval(
            @PathVariable("k1") @NonNull String k1,
            @PathVariable("k2") @NonNull String k2,
            @PathVariable("k3") @NonNull String k3,
            @PathVariable("start") @NonNull LocalDateTime start,
            @PathVariable("end") @NonNull LocalDateTime end,
            @RequestBody @NonNull Value value) {
        localDateTimeIntervalValidator.validate(start, end);
        if(tripleService.exists(k1, k2, k3, start, end)) {
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
            @PathVariable("start") @NonNull LocalDateTime start,
            @PathVariable("end") @NonNull LocalDateTime end) {
        localDateTimeIntervalValidator.validate(start, end);
        tripleService.findOne(k1, k2, k3, start, end)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Triple not found: %s/%s/%s/%s/%s", k1, k2, k3, start, end)));
    }

    public long localDateTimeToEpoch(LocalDateTime ldt) {
        return ldt.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
    }

    @Transactional(readOnly = true)
    @GetMapping("/{k1}/{k2}/{k3}/{start}/{end}")
    public List<Value> getTriplesWithInterval(
            @PathVariable("k1") @NonNull String k1,
            @PathVariable("k2") @NonNull String k2,
            @PathVariable("k3") @NonNull String k3,
            @PathVariable("start") @NonNull LocalDateTime start,
            @PathVariable("end") @NonNull LocalDateTime end) {
        localDateTimeIntervalValidator.validate(start, end);

        Interval interval = new Interval(localDateTimeToEpoch(start), localDateTimeToEpoch(end));
        return tripleService.findById(k1, k2, k3)
                .filter(tripleWithInterval -> {
                    Interval jpaInterval = new Interval(
                            localDateTimeToEpoch(tripleWithInterval.getId().getStart()),
                            localDateTimeToEpoch(tripleWithInterval.getId().getEnd()));
                    return jpaInterval.overlaps(interval);
                })
                .map(triple -> conversionService.convert(triple, Value.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @GetMapping("/{k1}/{k2}/{k3}")
    public List<Value> getTriplesById(
            @PathVariable("k1") @NonNull String k1,
            @PathVariable("k2") @NonNull String k2,
            @PathVariable("k3") @NonNull String k3) {
        return tripleService.findById(k1, k2, k3)
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