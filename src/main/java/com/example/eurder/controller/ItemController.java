package com.example.eurder.controller;

import com.example.eurder.dto.CreateItemDto;
import com.example.eurder.dto.ItemDto;
import com.example.eurder.service.AdminService;
import com.example.eurder.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/items")
public class ItemController {
    private final AdminService adminService;
    private final ItemService itemService;

    public ItemController(AdminService adminService, ItemService itemService) {
        this.adminService = adminService;
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto createItem(@RequestHeader String email, @RequestHeader String password, @Valid @RequestBody CreateItemDto createItemDto) {
        adminService.authenticate(email, password);

        return itemService.createItem(createItemDto);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable Integer id) {
        return itemService.getItem(id);
    }
}
