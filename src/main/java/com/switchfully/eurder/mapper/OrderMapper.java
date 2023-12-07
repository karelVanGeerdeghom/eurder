package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.dto.OrderDto;
import com.switchfully.eurder.dto.OrderLineDto;
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
