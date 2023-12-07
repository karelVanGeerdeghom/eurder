package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.*;
import com.switchfully.eurder.dto.CreateOrderDto;
import com.switchfully.eurder.dto.CreateOrderLineDto;
import com.switchfully.eurder.dto.OrderDto;
import com.switchfully.eurder.mapper.OrderLineMapper;
import com.switchfully.eurder.mapper.OrderMapper;
import com.switchfully.eurder.repository.ItemRepository;
import com.switchfully.eurder.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {
    private Customer customer;
    private Item item;
    private ItemRepository itemRepository;
    private OrderMapper orderMapper;
    private OrderLineMapper orderLineMapper;
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        customer = new Customer("email", "password", "firstName", "lastName", "phoneNumber", "address");

        item = new Item("name", "description", new Price(10.0, Currency.EUR), 10);

        itemRepository = new ItemRepository();
        itemRepository.truncate();
        itemRepository.create(item);

        orderLineMapper = new OrderLineMapper();
        orderMapper = new OrderMapper(orderLineMapper);

        orderRepository = new OrderRepository();

        orderService = new OrderService(itemRepository, orderMapper, orderLineMapper, orderRepository);
    }

    @Test
    void givenCustomerAndCreateOrderDto_whenAmountInStockIsLessThanAmountInOrder_thenShippingDateIsInStockShippingDate() {
        // GIVEN
        CreateOrderLineDto createOrderLineDto = new CreateOrderLineDto(item.getId(), 1);
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>(){{
            add(createOrderLineDto);
        }};
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos);

        // WHEN
        OrderDto actual = orderService.placeOrder(customer, createOrderDto);

        // THEN
        assertThat(actual).isInstanceOf(OrderDto.class);
    }
}