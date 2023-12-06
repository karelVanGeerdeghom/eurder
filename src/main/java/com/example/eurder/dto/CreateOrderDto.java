package com.example.eurder.dto;

import java.time.LocalDate;
import java.util.List;

public class CreateOrderDto {
    private List<CreateOrderLineDto> createOrderLineDtos;

    public CreateOrderDto() {
    }

    public List<CreateOrderLineDto> getCreateOrderLineDtos() {
        return createOrderLineDtos;
    }
}
