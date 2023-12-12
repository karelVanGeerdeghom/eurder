package com.switchfully.eurder.dto;

import com.switchfully.eurder.domain.Currency;
import com.switchfully.eurder.domain.Price;

import java.time.LocalDate;
import java.util.List;

public class OrderDto {
    private final Integer id;
    private final Integer customerId;
    private final String customerAddress;
    private final List<OrderLineDto> orderLineDtos;
    private final LocalDate orderDate;
    private final Price totalPrice;

    public OrderDto(Integer id, Integer customerId, String customerAddress, List<OrderLineDto> orderLineDtos, LocalDate orderDate, Price totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.customerAddress = customerAddress;
        this.orderLineDtos = orderLineDtos;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
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

    public Price getTotalPrice() {
        return totalPrice;
    }
}
