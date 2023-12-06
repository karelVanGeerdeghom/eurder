package com.example.eurder.mapper;

import com.example.eurder.domain.OrderLine;
import com.example.eurder.dto.CreateOrderLineDto;
import com.example.eurder.dto.OrderLineDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderLineMapper {
    private ItemMapper itemMapper;

    public OrderLineMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public OrderLineDto orderLineToOrderLineDto(OrderLine orderLine) {
        return new OrderLineDto(itemMapper.itemToItemDto(orderLine.getItem()), orderLine.getAmountInOrder(), orderLine.getShippingDate());
    }

    public OrderLine createOrderLineDtoToOrderLine(CreateOrderLineDto createOrderLineDto, LocalDate shippingDate) {
        return new OrderLine(itemMapper.itemDtoToItem(createOrderLineDto.getItemDto()), createOrderLineDto.getAmountInOrder(), shippingDate);
    }
}
