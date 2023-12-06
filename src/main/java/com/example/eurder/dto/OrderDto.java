package com.example.eurder.dto;

import com.example.eurder.domain.OrderLine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private Integer id;
    private Integer customerId;
    private LocalDate orderDate;
    private List<OrderLineDto> orderLineDtos;

    public OrderDto(Integer id, Integer customerId, LocalDate orderDate, List<OrderLineDto> orderLineDtos) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderLineDtos = orderLineDtos;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public List<OrderLineDto> getOrderLineDtos() {
        return orderLineDtos;
    }
}
