package com.example.eurder.domain;

import java.util.UUID;

public class Item {
    private Integer id;
    private String name;
    private String description;
    private Price price;
    private int amountInStock;

    public Item(String name, String description, Price price, int amountInStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountInStock = amountInStock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
