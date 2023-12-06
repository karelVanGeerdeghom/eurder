package com.example.eurder.mapper;

import com.example.eurder.domain.OrderLine;
import com.example.eurder.dto.OrderLineDto;
import org.springframework.stereotype.Component;

@Component
public class OrderLineMapper {
    public static OrderLineDto orderLineToOrderLineDto(OrderLine orderLine) {
        return new OrderLineDto(orderLine.getItemId(), orderLine.getItemName(), orderLine.getItemPrice(), orderLine.getAmountInOrder(), orderLine.getShippingDate());
    }
}
