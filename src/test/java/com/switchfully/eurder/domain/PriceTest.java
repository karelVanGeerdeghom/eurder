package com.switchfully.eurder.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {
    @Test
    void givenAmountAndCurrency_whenCreatePrice_thenGetPrice() {
        // GIVEN
        double amount = 10.0;
        Currency currency = Currency.EUR;

        // WHEN
        Price actual = new Price(amount, currency);

        // THEN
        assertThat(actual).isInstanceOf(Price.class);
        assertThat(actual.getAmount()).isEqualTo(amount);
        assertThat(actual.getCurrency()).isEqualTo(currency);
    }
}