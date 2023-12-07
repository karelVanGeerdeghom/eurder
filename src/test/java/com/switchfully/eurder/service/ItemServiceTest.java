package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.Currency;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.Price;
import com.switchfully.eurder.domain.StockIndicator;
import com.switchfully.eurder.dto.CreateItemDto;
import com.switchfully.eurder.dto.ItemDto;
import com.switchfully.eurder.dto.ItemStockIndicatorDto;
import com.switchfully.eurder.dto.UpdateItemDto;
import com.switchfully.eurder.exception.UnknownItemIdException;
import com.switchfully.eurder.mapper.ItemMapper;
import com.switchfully.eurder.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemServiceTest {
    private Item item;
    private ItemMapper itemMapper;
    private ItemRepository itemRepository;
    private ItemService itemService;

    @BeforeEach
    public void setup() {
        item = new Item("name", "description", new Price(10.0, Currency.EUR), 10);

        itemMapper = new ItemMapper();

        itemRepository = new ItemRepository();
        itemRepository.truncate();
        itemRepository.create(item);

        itemService = new ItemService(itemMapper, itemRepository);
    }

    @Test
    void givenCreateItemDto_whenCreateItem_thenGetItemDto() {
        // GIVEN
        String name = "name";
        String description = "description";
        Price price = new Price(10.0, Currency.EUR);
        int amountInStock = 10;
        CreateItemDto createItemDto = new CreateItemDto(name, description, price, amountInStock);

        // WHEN
        ItemDto actual = itemService.createItem(createItemDto);

        // THEN
        assertThat(actual).isInstanceOf(ItemDto.class);
        assertThat(actual.getId()).isEqualTo(2);
        assertThat(actual.getName()).isEqualTo(createItemDto.getName());
        assertThat(actual.getDescription()).isEqualTo(createItemDto.getDescription());
        assertThat(actual.getPrice()).isEqualTo(createItemDto.getPrice());
        assertThat(actual.getAmountInStock()).isEqualTo(createItemDto.getAmountInStock());
    }

    @Test
    void givenUpdateItemDto_whenUpdateItem_thenGetItemDto() {
        // GIVEN
        String name = "newName";
        String description = "newDescription";
        Price price = new Price(1.0, Currency.EUR);
        int amountInStock = 1;
        UpdateItemDto updateItemDto = new UpdateItemDto(name, description, price, amountInStock);

        // WHEN
        ItemDto actual = itemService.updateItem(1, updateItemDto);

        // THEN
        assertThat(actual).isInstanceOf(ItemDto.class);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getName()).isEqualTo(updateItemDto.getName());
        assertThat(actual.getDescription()).isEqualTo(updateItemDto.getDescription());
        assertThat(actual.getPrice()).isEqualTo(updateItemDto.getPrice());
        assertThat(actual.getAmountInStock()).isEqualTo(updateItemDto.getAmountInStock());
    }

    @Test
    void givenExistingId_whenGetItemById_thenGetItemWithGivenId() {
        // GIVEN
        Integer id = 1;

        // WHEN
        ItemDto actual = itemService.getById(id);

        // THEN
        assertThat(actual).isInstanceOf(ItemDto.class);
        assertThat(actual.getId()).isEqualTo(id);
    }

    @Test
    void givenWrongId_whenGetItemById_thenThrowUnknownItemIdException() {
        // GIVEN
        Integer id = 0;

        // WHEN + THEN
        assertThatThrownBy(() -> itemService.getById(id)).isInstanceOf(UnknownItemIdException.class);
    }

    @Test
    void whenGetAllItems_thenGetAllItemDtos() {
        // WHEN
        List<ItemDto> actual = itemService.getAllItems();

        // THEN
        assertThat(actual).containsExactly(itemMapper.itemToItemDto(item));
    }

    @Test
    void givenMultipleItems_whenGetAllItemStockIndicators_thenGetAllItemStockIndicatorsSortedByStockIndicator() {
        // GIVEN
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 15));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 8));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 6));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 4));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 2));

        // WHEN
        List<ItemStockIndicatorDto> actual = itemService.getAllItemStockIndicators(null);
        List<ItemStockIndicatorDto> expected = new ArrayList<>(){{
            add(new ItemStockIndicatorDto(6, "name", StockIndicator.STOCK_LOW));
            add(new ItemStockIndicatorDto(5, "name", StockIndicator.STOCK_LOW));
            add(new ItemStockIndicatorDto(4, "name", StockIndicator.STOCK_MEDIUM));
            add(new ItemStockIndicatorDto(3, "name", StockIndicator.STOCK_MEDIUM));
            add(new ItemStockIndicatorDto(1, "name", StockIndicator.STOCK_HIGH));
            add(new ItemStockIndicatorDto(2, "name", StockIndicator.STOCK_HIGH));
        }};

        // THEN
        assertThat(actual).containsSequence(expected);
    }

    @Test
    void givenMultipleItems_whenGetAllItemStockIndicatorsWithLowStock_thenGetAllItemStockIndicatorsSortedByStockIndicatorWithLowStock() {
        // GIVEN
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 15));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 8));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 6));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 4));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 2));

        // WHEN
        List<ItemStockIndicatorDto> actual = itemService.getAllItemStockIndicators(StockIndicator.STOCK_LOW);
        List<ItemStockIndicatorDto> expected = new ArrayList<>(){{
            add(new ItemStockIndicatorDto(6, "name", StockIndicator.STOCK_LOW));
            add(new ItemStockIndicatorDto(5, "name", StockIndicator.STOCK_LOW));
        }};

        // THEN
        assertThat(actual).containsSequence(expected);
    }

    @Test
    void givenMultipleItems_whenGetAllItemStockIndicatorsWithMediumStock_thenGetAllItemStockIndicatorsSortedByStockIndicatorWithMediumStock() {
        // GIVEN
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 15));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 8));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 6));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 4));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 2));

        // WHEN
        List<ItemStockIndicatorDto> actual = itemService.getAllItemStockIndicators(StockIndicator.STOCK_MEDIUM);
        List<ItemStockIndicatorDto> expected = new ArrayList<>(){{
            add(new ItemStockIndicatorDto(4, "name", StockIndicator.STOCK_MEDIUM));
            add(new ItemStockIndicatorDto(3, "name", StockIndicator.STOCK_MEDIUM));
        }};

        // THEN
        assertThat(actual).containsSequence(expected);
    }

    @Test
    void givenMultipleItems_whenGetAllItemStockIndicatorsWithHighStock_thenGetAllItemStockIndicatorsSortedByStockIndicatorWithHighStock() {
        // GIVEN
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 15));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 8));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 6));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 4));
        itemService.createItem(new CreateItemDto("name", "description", new Price(10.0, Currency.EUR), 2));

        // WHEN
        List<ItemStockIndicatorDto> actual = itemService.getAllItemStockIndicators(StockIndicator.STOCK_HIGH);
        List<ItemStockIndicatorDto> expected = new ArrayList<>(){{
            add(new ItemStockIndicatorDto(1, "name", StockIndicator.STOCK_HIGH));
            add(new ItemStockIndicatorDto(2, "name", StockIndicator.STOCK_HIGH));
        }};

        // THEN
        assertThat(actual).containsSequence(expected);
    }
}