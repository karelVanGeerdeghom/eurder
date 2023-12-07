package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.Currency;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.Price;
import com.switchfully.eurder.dto.CreateItemDto;
import com.switchfully.eurder.dto.ItemDto;
import com.switchfully.eurder.dto.UpdateItemDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemMapperTest {
    private ItemMapper itemMapper = new ItemMapper();

    @Test
    void givenItem_whenMapItemToItemDto_thenGetItemDto() {
        // GIVEN
        String name = "name";
        String description = "description";
        Price price = new Price(10.0, Currency.EUR);
        int amountInStock = 10;
        Item item = new Item(name, description, price, amountInStock);

        // WHEN
        ItemDto actual = itemMapper.itemToItemDto(item);

        // THEN
        assertThat(actual).isInstanceOf(ItemDto.class);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getName()).isEqualTo(item.getName());
        assertThat(actual.getDescription()).isEqualTo(item.getDescription());
        assertThat(actual.getPrice()).isEqualTo(item.getPrice());
        assertThat(actual.getAmountInStock()).isEqualTo(item.getAmountInStock());
    }

    @Test
    void givenCreateItemDto_whenMapCreateItemDtoToItem_thenGetItem() {
        // GIVEN
        String name = "name";
        String description = "description";
        Price price = new Price(10.0, Currency.EUR);
        int amountInStock = 10;
        CreateItemDto createItemDto = new CreateItemDto(name, description, price, amountInStock);

        // WHEN
        Item actual = itemMapper.createItemDtoToItem(createItemDto);

        // THEN
        assertThat(actual).isInstanceOf(Item.class);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getName()).isEqualTo(createItemDto.getName());
        assertThat(actual.getDescription()).isEqualTo(createItemDto.getDescription());
        assertThat(actual.getPrice()).isEqualTo(createItemDto.getPrice());
        assertThat(actual.getAmountInStock()).isEqualTo(createItemDto.getAmountInStock());
    }

    @Test
    void givenUpdateItemDto_whenMapUpdateItemDtoToItem_thenGetItem() {
        // GIVEN
        String name = "name";
        String description = "description";
        Price price = new Price(10.0, Currency.EUR);
        int amountInStock = 10;
        Item item = new Item(name, description, price, amountInStock);
        UpdateItemDto updateItemDto = new UpdateItemDto(name, description, price, amountInStock);

        // WHEN
        Item actual = itemMapper.updateItemDtoToItem(item, updateItemDto);

        // THEN
        assertThat(actual).isInstanceOf(Item.class);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getName()).isEqualTo(updateItemDto.getName());
        assertThat(actual.getDescription()).isEqualTo(updateItemDto.getDescription());
        assertThat(actual.getPrice()).isEqualTo(updateItemDto.getPrice());
        assertThat(actual.getAmountInStock()).isEqualTo(updateItemDto.getAmountInStock());
    }
}