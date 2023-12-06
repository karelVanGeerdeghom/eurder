package com.example.eurder.dto;

import com.example.eurder.domain.Price;

import java.time.LocalDate;

public class OrderLineDto {
    private Integer itemId;
    private String itemName;
    private Price itemPrice;
    private int amountInOrder;
    private LocalDate shippingDate;

    public OrderLineDto(Integer itemId, String itemName, Price itemPrice, int amountInOrder, LocalDate shippingDate) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.amountInOrder = amountInOrder;
        this.shippingDate = shippingDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Price getItemPrice() {
        return itemPrice;
    }

    public int getAmountInOrder() {
        return amountInOrder;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }
}
