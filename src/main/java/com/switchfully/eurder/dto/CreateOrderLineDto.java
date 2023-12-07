package com.switchfully.eurder.dto;

import jakarta.validation.constraints.NotNull;

public class CreateOrderLineDto {
    @NotNull
    private Integer itemId;
    @NotNull
    private Integer amountInOrder;

    public CreateOrderLineDto(Integer itemId, Integer amountInOrder) {
        this.itemId = itemId;
        this.amountInOrder = amountInOrder;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getAmountInOrder() {
        return amountInOrder;
    }
}
