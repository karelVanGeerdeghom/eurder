package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.*;
import com.switchfully.eurder.dto.CreateOrderDto;
import com.switchfully.eurder.dto.CreateOrderLineDto;
import com.switchfully.eurder.dto.OrderDto;
import com.switchfully.eurder.exception.UnknownItemIdException;
import com.switchfully.eurder.mapper.OrderLineMapper;
import com.switchfully.eurder.mapper.OrderMapper;
import com.switchfully.eurder.repository.ItemRepository;
import com.switchfully.eurder.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderServiceTest {
    private Customer customer;
    private Item itemOne;
    private Item itemTwo;
    private ItemRepository itemRepository;
    private OrderMapper orderMapper;
    private OrderLineMapper orderLineMapper;
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        customer = new Customer("email", "password", "firstName", "lastName", "phoneNumber", "address");

        itemOne = new Item("name", "description", new Price(10.0, Currency.EUR), 10);
        itemTwo = new Item("name", "description", new Price(10.0, Currency.EUR), 10);

        itemRepository = new ItemRepository();
        itemRepository.truncate();
        itemRepository.create(itemOne);
        itemRepository.create(itemTwo);

        orderLineMapper = new OrderLineMapper();
        orderMapper = new OrderMapper(orderLineMapper);

        orderRepository = new OrderRepository();

        orderService = new OrderService(itemRepository, orderMapper, orderLineMapper, orderRepository);
    }

    @Test
    void verifyShippingDaysInStock() {
        assertThat(OrderService.SHIPPING_DAYS_IN_STOCK).isEqualTo(1);
    }

    @Test
    void verifyShippingDaysNotInStock() {
        assertThat(OrderService.SHIPPING_DAYS_NOT_IN_STOCK).isEqualTo(7);
    }

    @Test
    void givenCustomerAndCreateOrderDto_whenPlaceOrder_thenGetOrderDto() {
        // GIVEN
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 1));
            add(new CreateOrderLineDto(itemTwo.getId(), 1));
        }};
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos);

        // WHEN
        OrderDto actual = orderService.placeOrder(customer, createOrderDto);

        // THEN
        assertThat(actual).isInstanceOf(OrderDto.class);
        assertThat(actual.getOrderLineDtos()).hasSize(2);
        assertThat(itemOne.getAmountInStock()).isEqualTo(9);
        assertThat(itemTwo.getAmountInStock()).isEqualTo(9);
    }

    @Test
    void givenCustomerAndCreateOrderDtoWithUnknownItemId_whenPlaceOrder_thenThrowUnknownItemIdException() {
        // GIVEN
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>(){{
            add(new CreateOrderLineDto(10, 1));
        }};
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos);

        // WHEN + THEN
        assertThatThrownBy(() -> orderService.placeOrder(customer, createOrderDto)).isInstanceOf(UnknownItemIdException.class);
    }

    @Test
    void givenCustomerAndCreateOrderDto_whenPlaceOrderAndAmountInOrderIsLessThanAmountInStock_thenShippingDateIsInStockShippingDate() {
        // GIVEN
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 1));
        }};
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos);

        // WHEN
        OrderDto actual = orderService.placeOrder(customer, createOrderDto);

        // THEN
        assertThat(actual.getOrderLineDtos().getFirst().getShippingDate()).isEqualTo(LocalDate.now().plusDays(OrderService.SHIPPING_DAYS_IN_STOCK));
    }

    @Test
    void givenCustomerAndCreateOrderDto_whenPlaceOrderAndAmountInOrderIsMoreThanAmountInStock_thenShippingDateIsNotInStockShippingDate() {
        // GIVEN
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 100));
        }};
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos);

        // WHEN
        OrderDto actual = orderService.placeOrder(customer, createOrderDto);

        // THEN
        assertThat(actual.getOrderLineDtos().getFirst().getShippingDate()).isEqualTo(LocalDate.now().plusDays(OrderService.SHIPPING_DAYS_NOT_IN_STOCK));
    }
}