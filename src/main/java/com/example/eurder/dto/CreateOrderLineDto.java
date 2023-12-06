package com.example.eurder.dto;

import java.time.LocalDate;

public class CreateOrderLineDto {
    private Integer itemId;
    private int amountInOrder;

    public CreateOrderLineDto(Integer itemId, int amountInOrder) {
        this.itemId = itemId;
        this.amountInOrder = amountInOrder;
    }

    public Integer getItemId() {
        return itemId;
    }

    public int getAmountInOrder() {
        return amountInOrder;
    }
}
