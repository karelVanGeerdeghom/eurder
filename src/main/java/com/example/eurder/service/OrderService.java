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
    
    private ItemMapper itemMapper;
    private OrderMapper orderMapper;
    private OrderLineMapper orderLineMapper;
    private OrderRepository orderRepository;
    private ItemService itemService;

    public OrderService(ItemMapper itemMapper, OrderMapper orderMapper, OrderLineMapper orderLineMapper, OrderRepository orderRepository, ItemService itemService) {
        this.itemMapper = itemMapper;
        this.orderMapper = orderMapper;
        this.orderLineMapper = orderLineMapper;
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    public OrderDto createOrder(Customer customer, CreateOrderDto createOrderDto) throws UnknownItemIdException {
        LocalDate orderDate = LocalDate.now();

        Order newOrder = new Order(customer.getId(), orderDate);
        for (CreateOrderLineDto createOrderLineDto : createOrderDto.getCreateOrderLineDtos()) {
            newOrder.addOrderLine(createOrderLine(createOrderLineDto, orderDate));
        }

        return orderMapper.orderToOrderDto(orderRepository.create(newOrder));
    }

    private OrderLine createOrderLine(CreateOrderLineDto createOrderLineDto, LocalDate orderDate) throws UnknownItemIdException {
        ItemDto itemDto = itemService.getItem(createOrderLineDto.getItemId());
        Item item = itemMapper.itemDtoToItem(itemDto);

        LocalDate shippingDate = getShippingDate(item, createOrderLineDto.getAmountInOrder(), orderDate);

        return new OrderLine(item.getId(), item.getName(), item.getPrice(), createOrderLineDto.getAmountInOrder(), shippingDate);
    }

    private LocalDate getShippingDate(Item item, int amountInOrder, LocalDate orderDate) {
        return orderDate.plusDays(item.getAmountInStock() >= amountInOrder ? SHIPPING_DAYS_IN_STOCK : SHIPPING_DAYS_NOT_IN_STOCK);
    }
}
