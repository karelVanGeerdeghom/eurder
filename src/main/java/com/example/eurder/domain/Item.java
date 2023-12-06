package com.example.eurder.domain;

import org.springframework.util.SerializationUtils;

import java.io.Serializable;

public class Item implements Serializable {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public int getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(int amountInStock) {
        this.amountInStock = amountInStock;
    }

    public Item getSnapshot() {
        return SerializationUtils.clone(this);
    }
}
