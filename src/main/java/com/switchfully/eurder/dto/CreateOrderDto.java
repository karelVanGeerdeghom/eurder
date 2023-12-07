package com.switchfully.eurder.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class CreateOrderDto {
    private List<CreateOrderLineDto> createOrderLineDtos;
    @NotNull
    private LocalDate orderDate;

    public CreateOrderDto(List<CreateOrderLineDto> createOrderLineDtos, LocalDate orderDate) {
        this.createOrderLineDtos = createOrderLineDtos;
        this.orderDate = orderDate;
    }

    public List<CreateOrderLineDto> getCreateOrderLineDtos() {
        return createOrderLineDtos;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
