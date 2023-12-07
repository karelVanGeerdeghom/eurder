package com.switchfully.eurder.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateOrderDto {
    @NotNull
    @Size(min = 1)
    @Valid
    private List<CreateOrderLineDto> createOrderLineDtos;

    public CreateOrderDto() {}

    public CreateOrderDto(List<CreateOrderLineDto> createOrderLineDtos) {
        this.createOrderLineDtos = createOrderLineDtos;
    }

    public List<CreateOrderLineDto> getCreateOrderLineDtos() {
        return createOrderLineDtos;
    }
}
