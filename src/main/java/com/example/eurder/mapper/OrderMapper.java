package com.example.eurder.mapper;

import com.example.eurder.domain.Order;
import com.example.eurder.dto.OrderDto;
import com.example.eurder.dto.OrderLineDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderDto orderToOrderDto(Order order) {
        List<OrderLineDto> orderLineDtos = order.getOrderLines().stream().map(OrderLineMapper::orderLineToOrderLineDto).toList();

        return new OrderDto(order.getId(), order.getCustomerId(), order.getOrderDate(), orderLineDtos);
    }
}
