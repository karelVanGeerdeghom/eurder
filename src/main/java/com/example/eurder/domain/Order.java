package com.example.eurder.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Integer id;
    private Integer customerId;
    private String customerAddress;
    private LocalDate orderDate;
    private List<OrderLine> orderLines = new ArrayList<>();

    public Order(Integer customerId, String customerAddress, LocalDate orderDate) {
        this.customerId = customerId;
        this.customerAddress = customerAddress;
        this.orderDate = orderDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }
}
