package com.github.dnvriend.controllers;

import org.springframework.lang.NonNull;

public interface PersonService {
    Person validatePerson(@NonNull Person person);
}
