package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.domain.OrderLine;
import com.switchfully.eurder.dto.OrderDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperTest {
    private final OrderMapper orderMapper = new OrderMapper(new OrderLineMapper());

    @Test
    void givenOrder_whenMapOrderToOrderDto_thenGetOrderDto() {
        // GIVEN
        Integer customerId = 1;
        String customerAddress = "address";
        List<OrderLine> orderLines = new ArrayList<>();
        LocalDate orderDate = LocalDate.now();
        Order order = new Order(customerId, customerAddress, orderLines, orderDate);

        // WHEN
        OrderDto actual = orderMapper.orderToOrderDto(order);

        // THEN
        assertThat(actual).isInstanceOf(OrderDto.class);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getCustomerId()).isEqualTo(order.getCustomerId());
        assertThat(actual.getCustomerAddress()).isEqualTo(order.getCustomerAddress());
        assertThat(actual.getOrderLineDtos()).isEmpty();
        assertThat(actual.getOrderDate()).isEqualTo(order.getOrderDate());
    }
}