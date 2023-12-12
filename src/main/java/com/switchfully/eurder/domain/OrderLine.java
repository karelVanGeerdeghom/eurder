package com.switchfully.eurder.domain;

import java.time.LocalDate;

public class OrderLine {
    private final Integer itemId;
    private final String itemName;
    private final Price itemPrice;
    private final Integer amountInOrder;
    private final LocalDate shippingDate;

    public OrderLine(Integer itemId, String itemName, Price itemPrice, Integer amountInOrder, LocalDate shippingDate) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.amountInOrder = amountInOrder;
        this.shippingDate = shippingDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Price getItemPrice() {
        return itemPrice;
    }

    public Integer getAmountInOrder() {
        return amountInOrder;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public Price calculateTotalPrice() {
        return new Price(itemPrice.getAmount() * amountInOrder, Currency.EUR);
    }
}
