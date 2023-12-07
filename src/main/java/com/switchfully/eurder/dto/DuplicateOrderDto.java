package com.switchfully.eurder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class DuplicateOrderDto {
    @NotNull
    private LocalDate orderDate;

    public DuplicateOrderDto() {}

    public DuplicateOrderDto(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
