package com.example.eurder.mapper;

import com.example.eurder.domain.Item;
import com.example.eurder.domain.OrderLine;
import com.example.eurder.dto.CreateOrderLineDto;
import com.example.eurder.dto.OrderLineDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderLineMapper {
    public OrderLineDto orderLineToOrderLineDto(OrderLine orderLine) {
        return new OrderLineDto(orderLine.getItemId(), orderLine.getItemName(), orderLine.getItemPrice(), orderLine.getAmountInOrder(), orderLine.getShippingDate());
    }

    public OrderLine createOrderLineDtoToOrderLine(Item item, CreateOrderLineDto createOrderLineDto, LocalDate shippingDate) {
        return new OrderLine(item.getId(), item.getName(), item.getPrice(), createOrderLineDto.getAmountInOrder(), shippingDate);
    }
}
