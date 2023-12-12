package com.switchfully.eurder.controller;

import com.switchfully.eurder.domain.StockIndicator;
import com.switchfully.eurder.dto.CreateItemDto;
import com.switchfully.eurder.dto.ItemDto;
import com.switchfully.eurder.dto.ItemStockIndicatorDto;
import com.switchfully.eurder.dto.UpdateItemDto;
import com.switchfully.eurder.service.AdminService;
import com.switchfully.eurder.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/items")
public class ItemController {
    private final AdminService adminService;
    private final ItemService itemService;

    public ItemController(AdminService adminService, ItemService itemService) {
        this.adminService = adminService;
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@RequestHeader String email, @RequestHeader String password, @Valid @RequestBody CreateItemDto createItemDto) {
        adminService.authenticate(email, password);

        return itemService.createItem(createItemDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto updateItem(@RequestHeader String email, @RequestHeader String password, @PathVariable Integer id, @Valid @RequestBody UpdateItemDto updateItemDto) {
        adminService.authenticate(email, password);

        return itemService.updateItem(id, updateItemDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto getItem(@PathVariable Integer id) {
        return itemService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/stock")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemStockIndicatorDto> getAllItemStockIndicators(@RequestHeader String email, @RequestHeader String password, @RequestParam(required = false) StockIndicator stockIndicator) {
        adminService.authenticate(email, password);

        return itemService.getAllItemStockIndicators(stockIndicator);
    }
}
