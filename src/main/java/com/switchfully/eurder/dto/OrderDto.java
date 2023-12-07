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

    public Price getTotalPrice() {
        double totalPriceAmount = orderLineDtos.stream().reduce(0.0, (totalPrice, orderLineDto) -> totalPrice + orderLineDto.getTotalPrice().getAmount(), Double::sum);

        return new Price(totalPriceAmount, Currency.EUR);
    }
}
