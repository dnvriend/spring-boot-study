package com.github.dnvriend.controllers;

import com.github.dnvriend.status.ResourceNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    Map<Integer, com.github.dnvriend.controllers.v1.Person> v1People = new ConcurrentHashMap<>();
    Map<Integer, com.github.dnvriend.controllers.v2.Person> v2People = new ConcurrentHashMap<>();

    //
    // URL Versioning
    //
    @GetMapping("/v1/person/{userId}")
    public com.github.dnvriend.controllers.v1.Person getV1Person(
        @PathVariable("userId") int userId) {
        return Optional
            .ofNullable(v1People.get(userId))
            .orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with id %s", userId)));
    }

    @GetMapping("/v2/person/{userId}")
    public com.github.dnvriend.controllers.v2.Person getV2Person(
        @PathVariable("userId") int userId) {
        return Optional
            .ofNullable(v2People.get(userId))
            .orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with id %s", userId)));
    }

    @GetMapping("/v1/person")
    @ResponseStatus(HttpStatus.OK)
    public Collection<com.github.dnvriend.controllers.v1.Person> getAllV1Person() {
        return v1People.values();
    }

    @PutMapping("/v1/person")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putV1Person(@RequestBody com.github.dnvriend.controllers.v1.Person person) {
        v1People.put(person.getId(), person);
    }

    @GetMapping("/v2/person")
    @ResponseStatus(HttpStatus.OK)
    public Collection<com.github.dnvriend.controllers.v2.Person> getAllV2Person() {
        return v2People.values();
    }

    @PutMapping("/v2/person")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putV2Person(@RequestBody com.github.dnvriend.controllers.v2.Person person) {
        v2People.put(person.getId(), person);
    }

    // Parameter versioning
    @GetMapping(value = "/person/{userId}", params = {"version=1"})
    public com.github.dnvriend.controllers.v1.Person getPersonParamV1(
        @PathVariable("userId") int userId) {
        return getV1Person(userId);
    }

    @GetMapping(value = "/person/{userId}", params = {"version=2"})
    public com.github.dnvriend.controllers.v2.Person getPersonParamV2(
        @PathVariable("userId") int userId) {
        return getV2Person(userId);
    }

    @GetMapping(value = "/person", params = {"version=1"})
    public Collection<com.github.dnvriend.controllers.v1.Person> getAllV1PersonParam() {
        return getAllV1Person();
    }

    @GetMapping(value = "/person", params = {"version=2"})
    public Collection<com.github.dnvriend.controllers.v2.Person> getAllV2PersonParam() {
        return getAllV2Person();
    }

    // Custom header
    @GetMapping(value = "/person/{userId}", headers = {"X-API-VERSION=1"})
    public com.github.dnvriend.controllers.v1.Person getPersonHeaderV1(
        @PathVariable("userId") int userId) {
        return getV1Person(userId);
    }

    @GetMapping(value = "/person/{userId}", headers = {"X-API-VERSION=2"})
    public com.github.dnvriend.controllers.v2.Person getPersonHeaderV2(
        @PathVariable("userId") int userId) {
        return getV2Person(userId);
    }

    @GetMapping(value = "/person", headers = {"X-API-VERSION=1"})
    public Collection<com.github.dnvriend.controllers.v1.Person> getAllV1PersonHeader() {
        return getAllV1Person();
    }

    @GetMapping(value = "/person", headers = {"X-API-VERSION=2"})
    public Collection<com.github.dnvriend.controllers.v2.Person> getAllV2PersonHeader() {
        return getAllV2Person();
    }

    // Content Negotiation
    @GetMapping(value = "/person/{userId}", produces = {"application/vnd.company.app-v1+json"})
    public com.github.dnvriend.controllers.v1.Person getPersonContentTypeV1(
        @PathVariable("userId") int userId) {
        return getV1Person(userId);
    }

    @GetMapping(value = "/person", produces = {"application/vnd.company.app-v1+json"})
    public Collection<com.github.dnvriend.controllers.v1.Person> getAllV1PersonContentType() {
        return getAllV1Person();
    }

    @GetMapping(value = "/person", produces = {"application/vnd.company.app-v2+json"})
    public Collection<com.github.dnvriend.controllers.v2.Person> getAllV2PersonContentType() {
        return getAllV2Person();
    }
}
