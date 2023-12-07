package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.domain.OrderLine;
import com.switchfully.eurder.exception.UnknownOrderIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderRepositoryTest {
    private OrderRepository orderRepository = new OrderRepository();

    @BeforeEach
    void truncate() {
        orderRepository.truncate();
    }

    @Test
    void givenOrder_whenCreateOrder_thenGetCreatedOrderWithIdOne() {
        // GIVEN
        Integer customerId = 1;
        String customerAddress = "address";
        List<OrderLine> orderLines = new ArrayList<>();
        LocalDate orderDate = LocalDate.now();

        Order order = new Order(customerId, customerAddress, orderLines, orderDate);

        // WHEN
        Order actual = orderRepository.create(order);

        // THEN
        assertThat(actual).isInstanceOf(Order.class);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getCustomerId()).isEqualTo(customerId);
        assertThat(actual.getCustomerAddress()).isEqualTo(customerAddress);
        assertThat(actual.getOrderLines()).isEqualTo(orderLines);
        assertThat(actual.getOrderDate()).isEqualTo(orderDate);
    }

    @Test
    void givenExistingId_whenGetOrderById_thenGetOrderWithGivenId() {
        // GIVEN
        Integer id = 1;

        Integer customerId = 1;
        String customerAddress = "address";
        List<OrderLine> orderLines = new ArrayList<>();
        LocalDate orderDate = LocalDate.now();
        orderRepository.create(new Order(customerId, customerAddress, orderLines, orderDate));

        // WHEN
        Order actual = orderRepository.getById(id);

        // THEN
        assertThat(actual).isInstanceOf(Order.class);
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getCustomerId()).isEqualTo(customerId);
        assertThat(actual.getCustomerAddress()).isEqualTo(customerAddress);
        assertThat(actual.getOrderLines()).isEqualTo(orderLines);
        assertThat(actual.getOrderDate()).isEqualTo(orderDate);
    }

    @Test
    void givenUnknownId_whenGetOrderById_thenThrowUnknownOrderIdException() {
        // GIVEN
        Integer id = 1;

        // WHEN + THEN
        assertThatThrownBy(() -> orderRepository.getById(id)).isInstanceOf(UnknownOrderIdException.class);
    }
}