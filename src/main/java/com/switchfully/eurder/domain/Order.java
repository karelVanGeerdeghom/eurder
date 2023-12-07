package com.switchfully.eurder.domain;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private Integer id;
    private Integer customerId;
    private String customerAddress;
    private List<OrderLine> orderLines;
    private LocalDate orderDate;

    public Order(Integer customerId, String customerAddress, List<OrderLine> orderLines, LocalDate orderDate) {
        this.customerId = customerId;
        this.customerAddress = customerAddress;
        this.orderLines = orderLines;
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

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
