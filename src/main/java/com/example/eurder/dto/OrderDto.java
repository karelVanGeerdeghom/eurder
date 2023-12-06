package com.example.eurder.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDto {
    private Integer id;
    private Integer customerId;
    private String customerAddress;
    private LocalDate orderDate;
    private List<OrderLineDto> orderLineDtos;

    public OrderDto(Integer id, Integer customerId, String customerAddress, LocalDate orderDate, List<OrderLineDto> orderLineDtos) {
        this.id = id;
        this.customerId = customerId;
        this.customerAddress = customerAddress;
        this.orderDate = orderDate;
        this.orderLineDtos = orderLineDtos;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public List<OrderLineDto> getOrderLineDtos() {
        return orderLineDtos;
    }
}
