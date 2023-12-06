package com.example.eurder.domain;

import java.util.Objects;

public class Price {
    private double amount;
    private Currency currency;

    public Price(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Double.compare(amount, price.amount) == 0 && currency == price.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
