package com.github.dnvriend.service;

import org.springframework.stereotype.Service;

@Service
public class NameService {

    public String getUserName(String id) {
        return "Real user name";
    }
}
