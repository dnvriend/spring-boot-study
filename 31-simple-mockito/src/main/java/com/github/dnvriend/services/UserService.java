package com.github.dnvriend.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final NameService nameService;

    public UserService(NameService nameService) {
        this.nameService = nameService;
    }

    public String getUserName(String id) {
        return nameService.getUserName(id);
    }
}
