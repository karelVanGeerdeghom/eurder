package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Currency;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.Price;
import com.switchfully.eurder.exception.UnknownItemIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemRepositoryTest {
    private final ItemRepository itemRepository = new ItemRepository();

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
    void givenExistingId_whenGetItemById_thenGetItemWithGivenId() {
        // GIVEN
        Integer id = 1;

        String name = "name";
        String description = "description";
        Price price = new Price(10.0, Currency.EUR);
        int amountInStock = 10;
        itemRepository.create(new Item(name, description, price, amountInStock));

        // WHEN
        Item actual = itemRepository.getById(id);

        // THEN
        assertThat(actual).isInstanceOf(Item.class);
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getDescription()).isEqualTo(description);
        assertThat(actual.getPrice()).isEqualTo(price);
        assertThat(actual.getAmountInStock()).isEqualTo(amountInStock);
    }

    @Test
    void givenUnknownId_whenGetItemById_thenThrowUnknownItemIdException() {
        // GIVEN
        Integer id = 1;

        // WHEN + THEN
        assertThatThrownBy(() -> itemRepository.getById(id)).isInstanceOf(UnknownItemIdException.class);
    }

    @Test
    void givenMultipleItems_whenGetAllItems_thenGetAllItems() {
        // GIVEN
        Item itemOne = new Item("name", "description", new Price(10.0, Currency.EUR), 10);
        itemRepository.create(itemOne);

        Item itemTwo = new Item("name", "description", new Price(10.0, Currency.EUR), 10);
        itemRepository.create(itemTwo);

        // WHEN
        List<Item> actual = itemRepository.getAllItems();

        // THEN
        assertThat(actual).containsExactly(itemOne, itemTwo);

    }
}