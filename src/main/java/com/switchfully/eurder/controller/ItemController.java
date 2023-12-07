package com.switchfully.eurder.controller;

import com.switchfully.eurder.dto.CreateItemDto;
import com.switchfully.eurder.dto.ItemDto;
import com.switchfully.eurder.dto.UpdateItemDto;
import com.switchfully.eurder.service.AdminService;
import com.switchfully.eurder.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public ItemDto updateItem(@RequestHeader String email, @RequestHeader String password, @PathVariable Integer id, @Valid @RequestBody UpdateItemDto updateItemDto) {
        adminService.authenticate(email, password);

        return itemService.updateItem(id, updateItemDto);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable Integer id) {
        return itemService.getById(id);
    }

    @GetMapping
    public List<ItemDto> getItems() {
        return itemService.getAllItems();
    }
}
