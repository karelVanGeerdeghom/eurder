package com.switchfully.eurder.dto;

import com.switchfully.eurder.domain.Currency;
import com.switchfully.eurder.domain.Price;

import java.time.LocalDate;

public class OrderLineDto {
    private final Integer itemId;
    private final String itemName;
    private final Price itemPrice;
    private final Integer amountInOrder;
    private final LocalDate shippingDate;
    private final Price totalPrice;

    public OrderLineDto(Integer itemId, String itemName, Price itemPrice, Integer amountInOrder, LocalDate shippingDate, Price totalPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.amountInOrder = amountInOrder;
        this.shippingDate = shippingDate;
        this.totalPrice = totalPrice;
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

    public Price getTotalPrice() {
        return totalPrice;
    }
}
