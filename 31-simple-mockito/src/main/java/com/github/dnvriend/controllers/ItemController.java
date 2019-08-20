package com.github.dnvriend.controllers;

import com.github.dnvriend.domain.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    @GetMapping("/items/{id}")
    public Item getItem(@PathVariable int id) {
        return Item.builder()
            .id(id)
            .name("Ball")
            .price(10)
            .quantity(100)
            .build();
    }
}
