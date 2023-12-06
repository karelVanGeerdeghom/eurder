package com.example.eurder.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private Integer id;
    private CustomerDto customerDto;
    private LocalDate orderDate;
    private List<OrderLineDto> orderLineDtos;

    public OrderDto(Integer id, CustomerDto customerDto, LocalDate orderDate, List<OrderLineDto> orderLineDtos) {
        this.id = id;
        this.customerDto = customerDto;
        this.orderDate = orderDate;
        this.orderLineDtos = orderLineDtos;
    }

    public Integer getId() {
        return id;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public List<OrderLineDto> getOrderLineDtos() {
        return orderLineDtos;
    }
}
