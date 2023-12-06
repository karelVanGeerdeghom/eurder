package com.example.eurder.dto;

import com.example.eurder.domain.Price;

public class UpdateItemDto {
    private String name;
    private String description;
    private Price price;
    private int amountInStock;

    public UpdateItemDto(String name, String description, Price price, int amountInStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountInStock = amountInStock;
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
