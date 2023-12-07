package com.switchfully.eurder.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class DuplicateOrderDto {
    @NotNull
    private LocalDate orderDate;

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
