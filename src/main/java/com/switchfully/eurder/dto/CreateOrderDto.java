package com.switchfully.eurder.dto;

import java.util.List;

public class CreateOrderDto {
    private List<CreateOrderLineDto> createOrderLineDtos;

    public CreateOrderDto() {}

    public CreateOrderDto(List<CreateOrderLineDto> createOrderLineDtos) {
        this.createOrderLineDtos = createOrderLineDtos;
    }

    public List<CreateOrderLineDto> getCreateOrderLineDtos() {
        return createOrderLineDtos;
    }
}
