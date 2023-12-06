package com.example.eurder.domain;

import java.time.LocalDate;

public class OrderLine {
    private Item item;
    private int amountInOrder;
    private LocalDate shippingDate;

    public OrderLine(Item item, int amountInOrder, LocalDate shippingDate) {
        this.item = item;
        this.amountInOrder = amountInOrder;
        this.shippingDate = shippingDate;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getAmountInOrder() {
        return amountInOrder;
    }

    public void setAmountInOrder(int amountInOrder) {
        this.amountInOrder = amountInOrder;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }
}
