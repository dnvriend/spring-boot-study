package com.github.dnvriend.controller;

import org.springframework.lang.NonNull;

public interface PersonService {

    Person validatePerson(@NonNull Person person);
}
