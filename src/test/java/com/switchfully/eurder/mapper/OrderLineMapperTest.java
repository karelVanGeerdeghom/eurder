package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.Currency;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.OrderLine;
import com.switchfully.eurder.domain.Price;
import com.switchfully.eurder.dto.CreateOrderLineDto;
import com.switchfully.eurder.dto.OrderLineDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OrderLineMapperTest {
    private final OrderLineMapper orderLineMapper = new OrderLineMapper();

    @Test
    void givenOrderLine_whenMapOrderLineToOrderLineDto_thenGetOrderLineDto() {
        // GIVEN
        Integer itemId = 1;
        String itemName = "itemName";
        Price itemPrice = new Price(10.0, Currency.EUR);
        int amountInOrder = 10;
        LocalDate shippingDate = LocalDate.now();
        OrderLine orderLine = new OrderLine(itemId, itemName, itemPrice, amountInOrder, shippingDate);

        // WHEN
        OrderLineDto actual = orderLineMapper.orderLineToOrderLineDto(orderLine);

        // THEN
        assertThat(actual).isInstanceOf(OrderLineDto.class);
        assertThat(actual.getItemId()).isEqualTo(orderLine.getItemId());
        assertThat(actual.getItemName()).isEqualTo(orderLine.getItemName());
        assertThat(actual.getItemPrice()).isEqualTo(orderLine.getItemPrice());
        assertThat(actual.getAmountInOrder()).isEqualTo(orderLine.getAmountInOrder());
        assertThat(actual.getShippingDate()).isEqualTo(orderLine.getShippingDate());
    }

    @Test
    void givenCreateOrderLineDto_whenMapCreateOrderLineDtoToOrderLine_thenGetOrderLine() {
        // GIVEN
        String name = "name";
        String description = "description";
        Price price = new Price(10.0, Currency.EUR);
        int amountInStock = 10;
        Item item = new Item(name, description, price, amountInStock);

        int amountInOrder = 1;
        CreateOrderLineDto createOrderLineDto = new CreateOrderLineDto(item.getId(), amountInOrder);

        LocalDate shippingDate = LocalDate.now();

        // WHEN
        OrderLine actual = orderLineMapper.createOrderLineDtoToOrderLine(item, createOrderLineDto, shippingDate);

        // THEN
        assertThat(actual).isInstanceOf(OrderLine.class);
        assertThat(actual.getItemId()).isEqualTo(createOrderLineDto.getItemId());
        assertThat(actual.getItemName()).isEqualTo(item.getName());
        assertThat(actual.getItemPrice()).isEqualTo(item.getPrice());
        assertThat(actual.getAmountInOrder()).isEqualTo(createOrderLineDto.getAmountInOrder());
        assertThat(actual.getShippingDate()).isEqualTo(shippingDate);
    }
}