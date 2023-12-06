package com.example.eurder.service;

import com.example.eurder.domain.Customer;
import com.example.eurder.dto.CreateOrderDto;
import com.example.eurder.dto.CreateOrderLineDto;
import com.example.eurder.dto.OrderDto;
import com.example.eurder.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    public static final int SHIPPING_DAYS_OFFSET_IN_STOCK = 1;
    public static final int SHIPPING_DAYS_OFFSET_NOT_IN_STOCK = 7;

    private OrderMapper orderMapper;

    public void createOrder(Customer customer, CreateOrderDto createOrderDto) {
        List<CreateOrderLineDto> createOrderLineDtos = createOrderDto.getCreateOrderLineDtos();

        System.out.println(createOrderLineDtos);
    }
}
