package com.github.dnvriend;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TripleService tripleService;

    static Value toJson(Triple triple) {
        return Value.builder()
            .value(triple.getValue())
            .build();
    }

    @GetMapping("/firstkey")
    public Stream<FirstKey> findFirstKey() {
        return tripleService.find(FirstKey.SELECT_ALL, FirstKey::fromArray);
    }

    @GetMapping("/secondkey")
    public Stream<SecondKey> findSecondKey() {
        return tripleService.find(SecondKey.SELECT_ALL, SecondKey::fromArray);
    }

    @GetMapping("/thirdkey")
    public Stream<ThirdKey> findThirdKey() {
        return tripleService.find(SecondKey.SELECT_ALL, ThirdKey::fromArray);
    }

    @PutMapping("/{k1}/{k2}/{k3}")
    public void putTriple(
        @PathVariable("k1") @NonNull String k1,
        @PathVariable("k2") @NonNull String k2,
        @PathVariable("k3") @NonNull String k3,
        @RequestBody @NonNull Value value) {
        if (tripleService.exists(k1, k2, k3)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Resource already exists");
        }
        tripleService.saveTriple(k1, k2, k3, value.getValue());
    }

    @PutMapping("/{k1}/{k2}/{k3}/{start}/{end}")
    public void putTripleWithInterval(
        @PathVariable("k1") @NonNull String k1,
        @PathVariable("k2") @NonNull String k2,
        @PathVariable("k3") @NonNull String k3,
        @PathVariable("start") @NonNull DateTime start,
        @PathVariable("end") @NonNull DateTime end,
        @RequestBody @NonNull Value value) {
        if (tripleService.exists(k1, k2, k3, start, end)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Resource already exists");
        }
        tripleService.saveTripleWithInterval(k1, k2, k3, start, end, value.getValue());
    }

    @RequestMapping(path = "/{k1}/{k2}/{k3}", method = RequestMethod.HEAD)
    public void checkTriple(
        @PathVariable("k1") @NonNull String k1,
        @PathVariable("k2") @NonNull String k2,
        @PathVariable("k3") @NonNull String k3) {
        tripleService.findOne(k1, k2, k3)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Triple not found: %s/%s/%s", k1, k2, k3)));
    }

    @Transactional(readOnly = true)
    @RequestMapping(path = "/{k1}/{k2}/{k3}/{start}/{end}", method = RequestMethod.HEAD)
    public void checkTripleByInterval(
        @PathVariable("k1") @NonNull String k1,
        @PathVariable("k2") @NonNull String k2,
        @PathVariable("k3") @NonNull String k3,
        @PathVariable("start") @NonNull DateTime start,
        @PathVariable("end") @NonNull DateTime end) {
        tripleService.findOne(k1, k2, k3, start, end)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Triple not found: %s/%s/%s/%s/%s", k1, k2, k3, start, end)));
    }

    @Transactional(readOnly = true)
    @GetMapping("/{k1}/{k2}/{k3}")
    public Optional<Value> getTriple(
        @PathVariable("k1") @NonNull String k1,
        @PathVariable("k2") @NonNull String k2,
        @PathVariable("k3") @NonNull String k3) {
        return tripleService.findOne(k1, k2, k3)
            .map(TripleController::toJson);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{k1}/{k2}/{k3}/{start}/{end}")
    public List<Value> getTriplesWithInterval(
        @PathVariable("k1") @NonNull String k1,
        @PathVariable("k2") @NonNull String k2,
        @PathVariable("k3") @NonNull String k3,
        @PathVariable("start") @NonNull DateTime start,
        @PathVariable("end") @NonNull DateTime end) {

        Interval interval = new Interval(start, end);
        return tripleService.findById(k1, k2, k3)
            .filter(tripleWithInterval -> {
                Interval jpaInterval = new Interval(tripleWithInterval.getStart(),
                    tripleWithInterval.getEnd());
                return jpaInterval.overlaps(interval);
            })
            .map(TripleController::toJson)
            .collect(Collectors.toList());
    }
}
