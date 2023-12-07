package com.switchfully.eurder.dto;

import com.switchfully.eurder.domain.StockIndicator;

import java.util.Objects;

public class ItemStockIndicatorDto {
    private final Integer id;
    private final String name;
    private final StockIndicator stockIndicator;

    public ItemStockIndicatorDto(Integer id, String name, StockIndicator stockIndicator) {
        this.id = id;
        this.name = name;
        this.stockIndicator = stockIndicator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStockIndicatorDto that = (ItemStockIndicatorDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && stockIndicator == that.stockIndicator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stockIndicator);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StockIndicator getStockIndicator() {
        return stockIndicator;
    }
}
