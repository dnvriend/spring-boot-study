package com.github.dnvriend;

import com.github.dnvriend.converters.DateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
class TripleKeyId implements Serializable {
    private String k1;
    private String k2;
    private String k3;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "triple")
class Triple {
    @EmbeddedId
    private TripleKeyId id;
    private String value;
    @Convert(converter = DateTimeConverter.class)
    private DateTime start;
    @Convert(converter = DateTimeConverter.class)
    private DateTime end;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Value {
    private String value;
}

interface TripleRepository extends JpaRepository<Triple, Long> {
    Stream<Triple> findById_K1AndId_K2AndId_K3(String k1, String k2, String k3);
}

@Service
class TripleService {
    @Autowired
    private TripleRepository tripleRepository;

    public Triple saveTriple(String k1, String k2, String k3, String value) {
        return tripleRepository.save(
                Triple.builder()
                        .id(TripleKeyId.builder()
                                .k1(k1)
                                .k2(k2)
                                .k3(k3)
                                .build())
                        .value(value)
                        .build());
    }

    public Triple saveTripleWithInterval(String k1, String k2, String k3, DateTime start, DateTime end, String value) {
        return tripleRepository.save(
                Triple.builder()
                        .id(TripleKeyId.builder()
                                .k1(k1)
                                .k2(k2)
                                .k3(k3)
                                .build())
                        .start(start)
                        .end(end)
                        .value(value)
                        .build());
    }

    public boolean exists(String k1, String k2, String k3) {
        return tripleRepository.exists(Example.of(
                Triple.builder()
                        .id(TripleKeyId.builder()
                                .k1(k1)
                                .k2(k2)
                                .k3(k3)
                                .build())
                        .build())
        );
    }

    public boolean exists(String k1, String k2, String k3, DateTime start, DateTime end) {
        return tripleRepository.exists(Example.of(
                Triple.builder()
                        .id(TripleKeyId.builder()
                                .k1(k1)
                                .k2(k2)
                                .k3(k3)
                                .build())
                        .start(start)
                        .end(end)
                        .build())
        );
    }

    public Optional<Triple> findOne(String k1, String k2, String k3) {
        return tripleRepository.findOne(Example.of(
                Triple.builder()
                        .id(TripleKeyId.builder()
                                .k1(k1)
                                .k2(k2)
                                .k3(k3)
                                .build())
                        .build())
        );
    }

    public Optional<Triple> findOne(String k1, String k2, String k3, DateTime start, DateTime end) {
        return tripleRepository.findOne(Example.of(
                Triple.builder()
                        .id(TripleKeyId.builder()
                                .k1(k1)
                                .k2(k2)
                                .k3(k3)
                                .build())
                        .start(start)
                        .end(end)
                        .build())
        );
    }

    public Stream<Triple> findById(String k1, String k2, String k3) {
        return tripleRepository.findById_K1AndId_K2AndId_K3(k1, k2, k3);
    }
}

@RestController
class TripleController {
    @Autowired
    private TripleService tripleService;

    static Value toJson(Triple triple) {
        return Value.builder()
                .value(triple.getValue())
                .build();
    }

    @PutMapping("/{k1}/{k2}/{k3}")
    public void putTriple(
            @PathVariable("k1") @NonNull String k1,
            @PathVariable("k2") @NonNull String k2,
            @PathVariable("k3") @NonNull String k3,
            @RequestBody @NonNull Value value) {
        if(tripleService.exists(k1, k2, k3)) {
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
        if(tripleService.exists(k1, k2, k3, start, end)) {
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
                    Interval jpaInterval = new Interval(tripleWithInterval.getStart(), tripleWithInterval.getEnd());
                    return jpaInterval.overlaps(interval);
                })
                .map(TripleController::toJson)
                .collect(Collectors.toList());
    }
}

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}