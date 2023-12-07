package com.switchfully.eurder.dto;

import com.switchfully.eurder.domain.Price;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateItemDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Price price;
    @NotNull
    private Integer amountInStock;

    public UpdateItemDto(String name, String description, Price price, Integer amountInStock) {
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

    public Integer getAmountInStock() {
        return amountInStock;
    }
}
