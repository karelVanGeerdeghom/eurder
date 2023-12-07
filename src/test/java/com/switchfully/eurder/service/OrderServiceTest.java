package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.*;
import com.switchfully.eurder.dto.CreateOrderDto;
import com.switchfully.eurder.dto.CreateOrderLineDto;
import com.switchfully.eurder.dto.OrderDto;
import com.switchfully.eurder.exception.InvalidAmountInOrderInOrderLineException;
import com.switchfully.eurder.exception.NoOrderLinesException;
import com.switchfully.eurder.exception.OrderIsNotForCustomerException;
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

import static org.assertj.core.api.Assertions.*;

class OrderServiceTest {
    private Customer customerOne;
    private Customer customerTwo;
    private Item itemOne;
    private Item itemTwo;
    private ItemRepository itemRepository;
    private OrderMapper orderMapper;
    private OrderLineMapper orderLineMapper;
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        customerOne = new Customer("customer.one@mail.com", "password", "firstName", "lastName", "phoneNumber", "address");
        customerOne.setId(1);

        customerTwo = new Customer("customer.two@mail.com", "password", "firstName", "lastName", "phoneNumber", "address");
        customerTwo.setId(2);

        itemOne = new Item("name", "description", new Price(10.0, Currency.EUR), 10);
        itemTwo = new Item("name", "description", new Price(10.0, Currency.EUR), 10);

        itemRepository = new ItemRepository();
        itemRepository.truncate();
        itemRepository.create(itemOne);
        itemRepository.create(itemTwo);

        orderLineMapper = new OrderLineMapper();
        orderMapper = new OrderMapper(orderLineMapper);

        orderRepository = new OrderRepository();
        orderRepository.truncate();

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
        LocalDate orderDate = LocalDate.now();
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 1));
            add(new CreateOrderLineDto(itemTwo.getId(), 1));
        }};
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos, orderDate);

        // WHEN
        OrderDto actual = orderService.placeOrder(customerOne, createOrderDto);

        // THEN
        assertThat(actual).isInstanceOf(OrderDto.class);
        assertThat(actual.getOrderLineDtos()).hasSize(2);
        assertThat(actual.getTotalPrice()).isEqualTo(new Price(20.0, Currency.EUR));
        assertThat(itemOne.getAmountInStock()).isEqualTo(9);
        assertThat(itemTwo.getAmountInStock()).isEqualTo(9);
    }

    @Test
    void givenCustomerAndCreateOrderDtoWithoutCreateOrderLineDtos_whenPlaceOrder_thenThrowNoOrderLinesException() {
        // GIVEN
        LocalDate orderDate = LocalDate.now();
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>();
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos, orderDate);

        // WHEN + THEN
        assertThatThrownBy(() -> orderService.placeOrder(customerOne, createOrderDto)).isInstanceOf(NoOrderLinesException.class);
    }

    @Test
    void givenCustomerAndCreateOrderDtoWithInvalidAmountInOrder_whenPlaceOrder_thenThrowInvalidAmountInOrderInOrderLineException() {
        // GIVEN
        LocalDate orderDate = LocalDate.now();
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>(){{
            add(new CreateOrderLineDto(10, 0));
        }};
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos, orderDate);

        // WHEN + THEN
        assertThatThrownBy(() -> orderService.placeOrder(customerOne, createOrderDto)).isInstanceOf(InvalidAmountInOrderInOrderLineException.class);
    }

    @Test
    void givenCustomerAndCreateOrderDtoWithUnknownItemId_whenPlaceOrder_thenThrowUnknownItemIdException() {
        // GIVEN
        LocalDate orderDate = LocalDate.now();
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>(){{
            add(new CreateOrderLineDto(10, 1));
        }};
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos, orderDate);

        // WHEN + THEN
        assertThatThrownBy(() -> orderService.placeOrder(customerOne, createOrderDto)).isInstanceOf(UnknownItemIdException.class);
    }

    @Test
    void givenCustomerAndCreateOrderDto_whenPlaceOrderAndAmountInOrderIsLessThanAmountInStock_thenShippingDateIsInStockShippingDate() {
        // GIVEN
        LocalDate orderDate = LocalDate.now();
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 1));
        }};
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos, orderDate);

        // WHEN
        OrderDto actual = orderService.placeOrder(customerOne, createOrderDto);

        // THEN
        assertThat(actual.getOrderLineDtos().getFirst().getShippingDate()).isEqualTo(LocalDate.now().plusDays(OrderService.SHIPPING_DAYS_IN_STOCK));
    }

    @Test
    void givenCustomerAndCreateOrderDto_whenPlaceOrderAndAmountInOrderIsMoreThanAmountInStock_thenShippingDateIsNotInStockShippingDate() {
        // GIVEN
        LocalDate orderDate = LocalDate.now();
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 100));
        }};
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos, orderDate);

        // WHEN
        OrderDto actual = orderService.placeOrder(customerOne, createOrderDto);

        // THEN
        assertThat(actual.getOrderLineDtos().getFirst().getShippingDate()).isEqualTo(LocalDate.now().plusDays(OrderService.SHIPPING_DAYS_NOT_IN_STOCK));
    }

    @Test
    void givenCustomerAndMultipleOrders_whenGetAllOrdersForCustomer_thenGetCustomerOrderDtos() {
        // GIVEN
        LocalDate orderDate = LocalDate.now();
        orderService.placeOrder(customerOne, new CreateOrderDto(new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 1));
        }}, orderDate));
        orderService.placeOrder(customerOne, new CreateOrderDto(new ArrayList<>(){{
            add(new CreateOrderLineDto(itemTwo.getId(), 1));
        }}, orderDate));

        // WHEN
        List<OrderDto> actual = orderService.getAllOrdersForCustomer(customerOne);

        // THEN
        assertThat(actual).hasSize(2);
        assertThat(actual).allSatisfy(orderDto -> assertThat(orderDto).isInstanceOf(OrderDto.class));
    }

    @Test
    void givenMultipleCustomersAndMultipleOrdersShippingToday_whenGetAllOrdersShippingToday_thenGetAllOrderDtosShippingToday() {
        // GIVEN
        LocalDate orderDate = LocalDate.now();
        orderService.placeOrder(customerOne, new CreateOrderDto(new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 1));
            add(new CreateOrderLineDto(itemTwo.getId(), 20));
        }}, orderDate));
        orderService.placeOrder(customerTwo, new CreateOrderDto(new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 1));
            add(new CreateOrderLineDto(itemTwo.getId(), 20));
        }}, orderDate));

        // WHEN
        List<OrderDto> actual = orderService.getAllOrdersShippingOnDate(orderDate.plusDays(1));

        // THEN
        assertThat(actual).hasSize(2);
        assertThat(actual).allSatisfy(orderDto -> assertThat(orderDto).isInstanceOf(OrderDto.class));
        assertThat(actual).allSatisfy(orderDto -> assertThat(orderDto.getOrderLineDtos()).hasSize(1));
    }

    @Test
    void givenExistingId_whenGetOrderById_thenGetOrderDtoWithGivenId() {
        // GIVEN
        Integer id = 1;

        LocalDate orderDate = LocalDate.now();
        orderService.placeOrder(customerOne, new CreateOrderDto(new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 1));
        }}, orderDate));

        // WHEN
        OrderDto actual = orderService.getById(id);

        // THEN
        assertThat(actual).isInstanceOf(OrderDto.class);
        assertThat(actual.getId()).isEqualTo(id);
    }

    @Test
    void givenMultipleCustomersAndMultipleOrders_whenValidateOrderByIdForCustomer_thenGetOrderDtoWithGivenId() {
        // GIVEN
        LocalDate orderDate = LocalDate.now().minusDays(1);
        orderService.placeOrder(customerOne, new CreateOrderDto(new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 1));
        }}, orderDate));
        orderService.placeOrder(customerTwo, new CreateOrderDto(new ArrayList<>(){{
            add(new CreateOrderLineDto(itemTwo.getId(), 1));
        }}, orderDate));

        // WHEN + THEN
        assertThatNoException().isThrownBy(() -> orderService.validateOrderByIdForCustomer(customerOne, 1));
    }

    @Test
    void givenMultipleCustomersAndMultipleOrders_whenValidateOrderByIdForAnotherCustomer_thenThrowOrderIsNotForCustomerException() {
        // GIVEN
        LocalDate orderDate = LocalDate.now().minusDays(1);
        orderService.placeOrder(customerOne, new CreateOrderDto(new ArrayList<>(){{
            add(new CreateOrderLineDto(itemOne.getId(), 1));
        }}, orderDate));
        orderService.placeOrder(customerTwo, new CreateOrderDto(new ArrayList<>(){{
            add(new CreateOrderLineDto(itemTwo.getId(), 1));
        }}, orderDate));

        // WHEN + THEN
        assertThatThrownBy(() -> orderService.validateOrderByIdForCustomer(customerOne, 2)).isInstanceOf(OrderIsNotForCustomerException.class);
    }
}