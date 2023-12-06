package com.example.eurder.dto;

import com.example.eurder.domain.Price;

public class ItemDto {
    private Integer id;
    private String name;
    private String description;
    private Price price;
    private int amountInStock;

    public ItemDto(Integer id, String name, String description, Price price, int amountInStock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountInStock = amountInStock;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    public int getAmountInStock() {
        return amountInStock;
    }
}
