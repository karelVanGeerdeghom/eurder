package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.OrderLine;
import com.switchfully.eurder.dto.CreateOrderLineDto;
import com.switchfully.eurder.dto.OrderLineDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderLineMapper {
    public OrderLineDto orderLineToOrderLineDto(OrderLine orderLine) {
        return new OrderLineDto(orderLine.getItemId(), orderLine.getItemName(), orderLine.getItemPrice(), orderLine.getAmountInOrder(), orderLine.getShippingDate());
    }

    public CreateOrderLineDto orderLineToCreateOrderLineDto(OrderLine orderLine) {
        return new CreateOrderLineDto(orderLine.getItemId(), orderLine.getAmountInOrder());
    }

    public OrderLine createOrderLineDtoToOrderLine(Item item, CreateOrderLineDto createOrderLineDto, LocalDate shippingDate) {
        return new OrderLine(item.getId(), item.getName(), item.getPrice(), createOrderLineDto.getAmountInOrder(), shippingDate);
    }
}
