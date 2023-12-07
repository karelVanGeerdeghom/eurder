package com.switchfully.eurder.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDto {
    private Integer id;
    private Integer customerId;
    private String customerAddress;
    private List<OrderLineDto> orderLineDtos;
    private LocalDate orderDate;

    public OrderDto(Integer id, Integer customerId, String customerAddress, List<OrderLineDto> orderLineDtos, LocalDate orderDate) {
        this.id = id;
        this.customerId = customerId;
        this.customerAddress = customerAddress;
        this.orderLineDtos = orderLineDtos;
        this.orderDate = orderDate;
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

    public List<OrderLineDto> getOrderLineDtos() {
        return orderLineDtos;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
