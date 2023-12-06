package com.example.eurder.repository;

import com.example.eurder.domain.Currency;
import com.example.eurder.domain.Item;
import com.example.eurder.domain.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {
    private ItemRepository itemRepository = new ItemRepository();

    @BeforeEach
    void truncate() {
        itemRepository.truncate();
    }

    @Test
    void givenItem_whenCreateItem_thenGetCreatedItemWithIdOne() {
        // GIVEN
        String name = "name";
        String description = "description";
        Price price = new Price(10.0, Currency.EUR);
        int amountInStock = 10;
        Item item = new Item(name, description, price, amountInStock);

        // WHEN
        Item actual = itemRepository.create(item);

        // THEN
        assertThat(actual).isInstanceOf(Item.class);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getDescription()).isEqualTo(description);
        assertThat(actual.getPrice()).isEqualTo(price);
        assertThat(actual.getAmountInStock()).isEqualTo(amountInStock);
    }

    @Test
    void givenId_whenGetItemById_thenGetItemWithGivenId() {
        // GIVEN
        String name = "name";
        String description = "description";
        Price price = new Price(10.0, Currency.EUR);
        int amountInStock = 10;
        itemRepository.create(new Item(name, description, price, amountInStock));

        // WHEN
        Item actual = itemRepository.getById(1);

        // THEN
        assertThat(actual).isInstanceOf(Item.class);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getDescription()).isEqualTo(description);
        assertThat(actual.getPrice()).isEqualTo(price);
        assertThat(actual.getAmountInStock()).isEqualTo(amountInStock);
    }
}