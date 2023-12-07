package com.switchfully.eurder.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateOrderLineDto {
    private Integer itemId;
    @Min(1)
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
