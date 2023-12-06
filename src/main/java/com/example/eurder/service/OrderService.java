package com.example.eurder.service;

import com.example.eurder.domain.Customer;
import com.example.eurder.domain.Item;
import com.example.eurder.domain.Order;
import com.example.eurder.domain.OrderLine;
import com.example.eurder.dto.CreateOrderDto;
import com.example.eurder.dto.CreateOrderLineDto;
import com.example.eurder.dto.ItemDto;
import com.example.eurder.dto.OrderDto;
import com.example.eurder.exception.UnknownItemIdException;
import com.example.eurder.mapper.ItemMapper;
import com.example.eurder.mapper.OrderLineMapper;
import com.example.eurder.mapper.OrderMapper;
import com.example.eurder.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderService {
    public static final int SHIPPING_DAYS_IN_STOCK = 1;
    public static final int SHIPPING_DAYS_NOT_IN_STOCK = 7;

    private ItemService itemService;
    private OrderMapper orderMapper;
    private OrderLineMapper orderLineMapper;
    private OrderRepository orderRepository;

    public OrderService(ItemService itemService, OrderMapper orderMapper, OrderLineMapper orderLineMapper, OrderRepository orderRepository) {
        this.itemService = itemService;
        this.orderMapper = orderMapper;
        this.orderLineMapper = orderLineMapper;
        this.orderRepository = orderRepository;
    }

    public OrderDto createOrder(Customer customer, CreateOrderDto createOrderDto) throws UnknownItemIdException {
        LocalDate orderDate = LocalDate.now();

        Order order = new Order(customer.getId(), customer.getAddress(), orderDate);
        for (CreateOrderLineDto createOrderLineDto : createOrderDto.getCreateOrderLineDtos()) {
            order.addOrderLine(getOrderLine(createOrderLineDto, orderDate));
        }

        return orderMapper.orderToOrderDto(orderRepository.create(order));
    }

    private OrderLine getOrderLine(CreateOrderLineDto createOrderLineDto, LocalDate orderDate) {
        ItemDto itemDto = itemService.getItem(createOrderLineDto.getItemId());
        LocalDate shippingDate = getShippingDate(itemDto, createOrderLineDto.getAmountInOrder(), orderDate);
        OrderLine orderLine = orderLineMapper.createOrderLineDtoToOrderLine(itemDto, createOrderLineDto, shippingDate);
        
        return orderLine;
    }
    
    private LocalDate getShippingDate(ItemDto itemDto, int amountInOrder, LocalDate orderDate) {
        return orderDate.plusDays(itemDto.getAmountInStock() >= amountInOrder ? SHIPPING_DAYS_IN_STOCK : SHIPPING_DAYS_NOT_IN_STOCK);
    }
}
