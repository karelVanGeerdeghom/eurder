package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    private final OrderLineMapper orderLineMapper;

    public OrderMapper(OrderLineMapper orderLineMapper) {
        this.orderLineMapper = orderLineMapper;
    }

    public OrderDto orderToOrderDto(Order order) {
        List<OrderLineDto> orderLineDtos = order.getOrderLines().stream().map(orderLineMapper::orderLineToOrderLineDto).toList();

        return new OrderDto(order.getId(), order.getCustomerId(), order.getCustomerAddress(), orderLineDtos, order.getOrderDate(), order.calculateTotalPrice());
    }

    public CreateOrderDto reOrderDtoToCreateOrderDto(Order order, ReOrderDto reOrderDto) {
        List<CreateOrderLineDto> createOrderLineDtos = order.getOrderLines().stream().map(orderLineMapper::orderLineToCreateOrderLineDto).toList();

        return new CreateOrderDto(createOrderLineDtos, reOrderDto.getOrderDate());
    }
}
