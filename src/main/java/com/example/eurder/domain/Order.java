package com.example.eurder.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Integer id;
    private Customer customer;
    private LocalDate orderDate;
    private List<OrderLine> orderLines = new ArrayList<>();

    public Order(Customer customer, LocalDate orderDate) {
        this.customer = customer;
        this.orderDate = orderDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
