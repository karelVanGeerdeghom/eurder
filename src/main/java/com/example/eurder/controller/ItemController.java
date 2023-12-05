package com.example.eurder.controller;

import com.example.eurder.domain.Currency;
import com.example.eurder.domain.Price;
import com.example.eurder.dto.ItemDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/items")
public class ItemController {
    @PostMapping
    public ItemDto createItem() {
        ItemDto itemDto = new ItemDto("id", "name", "description", new Price(0.0, Currency.EUR), 10);

        return itemDto;
    }

    @GetMapping("/{id}")
    public ItemDto getItem() {
        ItemDto itemDto = new ItemDto("id", "name", "description", new Price(0.0, Currency.EUR), 10);

        return itemDto;
    }
}
