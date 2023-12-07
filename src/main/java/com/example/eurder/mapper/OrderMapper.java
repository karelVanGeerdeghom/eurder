package com.example.eurder.mapper;

import com.example.eurder.domain.Order;
import com.example.eurder.dto.OrderDto;
import com.example.eurder.dto.OrderLineDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    private OrderLineMapper orderLineMapper;

    public OrderMapper(OrderLineMapper orderLineMapper) {
        this.orderLineMapper = orderLineMapper;
    }

    public OrderDto orderToOrderDto(Order order) {
        List<OrderLineDto> orderLineDtos = order.getOrderLines().stream().map(orderLine -> orderLineMapper.orderLineToOrderLineDto(orderLine)).toList();

        return new OrderDto(order.getId(), order.getCustomerId(), order.getCustomerAddress(), orderLineDtos, order.getOrderDate());
    }
}
