package com.switchfully.eurder.dto;

import java.util.List;

public class CreateOrderDto {
    private List<CreateOrderLineDto> createOrderLineDtos;

    public CreateOrderDto() {}

    public List<CreateOrderLineDto> getCreateOrderLineDtos() {
        return createOrderLineDtos;
    }
}
