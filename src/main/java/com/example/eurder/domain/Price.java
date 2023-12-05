package com.example.eurder.domain;

public class Price {
    private double amount;
    private Currency currency;

    public Price(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }
}
