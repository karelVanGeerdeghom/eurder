package com.example.eurder.dto;

import java.time.LocalDate;

public class CreateOrderLineDto {
    private ItemDto itemDto;
    private int amountInOrder;
    private LocalDate shippingDate;

    public CreateOrderLineDto(ItemDto itemDto, int amountInOrder, LocalDate shippingDate) {
        this.itemDto = itemDto;
        this.amountInOrder = amountInOrder;
        this.shippingDate = shippingDate;
    }

    public ItemDto getItemDto() {
        return itemDto;
    }

    public int getAmountInOrder() {
        return amountInOrder;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }
}
