package com.switchfully.eurder.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReOrderDto {
    @NotNull
    private LocalDate orderDate;

    public ReOrderDto() {
        // jackson
    }

    public ReOrderDto(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
