package com.example.eurder.service;

import com.example.eurder.domain.Customer;
import com.example.eurder.domain.Item;
import com.example.eurder.domain.Order;
import com.example.eurder.domain.OrderLine;
import com.example.eurder.dto.CreateOrderDto;
import com.example.eurder.dto.CreateOrderLineDto;
import com.example.eurder.dto.OrderDto;
import com.example.eurder.dto.OrderLineDto;
import com.example.eurder.exception.UnknownItemIdException;
import com.example.eurder.mapper.OrderLineMapper;
import com.example.eurder.mapper.OrderMapper;
import com.example.eurder.repository.ItemRepository;
import com.example.eurder.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    public static final int SHIPPING_DAYS_IN_STOCK = 1;
    public static final int SHIPPING_DAYS_NOT_IN_STOCK = 7;

    private ItemRepository itemRepository;
    private OrderMapper orderMapper;
    private OrderLineMapper orderLineMapper;
    private OrderRepository orderRepository;

    public OrderService(ItemRepository itemRepository, OrderMapper orderMapper, OrderLineMapper orderLineMapper, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.orderMapper = orderMapper;
        this.orderLineMapper = orderLineMapper;
        this.orderRepository = orderRepository;
    }

    public OrderDto placeOrder(Customer customer, CreateOrderDto createOrderDto) throws UnknownItemIdException {
        Order order = createOrder(customer, createOrderDto);
        updateStock(order);

        return orderMapper.orderToOrderDto(order);
    }

    private Order createOrder(Customer customer, CreateOrderDto createOrderDto) throws UnknownItemIdException {
        LocalDate orderDate = LocalDate.now();
        List<OrderLine> orderLines = createOrderLines(createOrderDto, orderDate);

        return orderRepository.create(new Order(customer.getId(), customer.getAddress(), orderLines, orderDate));
    }

    private List<OrderLine> createOrderLines(CreateOrderDto createOrderDto, LocalDate orderDate) throws UnknownItemIdException {
        return createOrderDto.getCreateOrderLineDtos().stream()
                .map(createOrderLineDto -> createOrderLine(createOrderLineDto, orderDate))
                .toList();
    }

    private OrderLine createOrderLine(CreateOrderLineDto createOrderLineDto, LocalDate orderDate) throws UnknownItemIdException {
        Item item = itemRepository.getById(createOrderLineDto.getItemId());
        LocalDate shippingDate = getShippingDate(item, createOrderLineDto.getAmountInOrder(), orderDate);
        OrderLine orderLine = orderLineMapper.createOrderLineDtoToOrderLine(item, createOrderLineDto, shippingDate);

        return orderLine;
    }

    private LocalDate getShippingDate(Item item, int amountInOrder, LocalDate orderDate) {
        return orderDate.plusDays(item.getAmountInStock() >= amountInOrder ? SHIPPING_DAYS_IN_STOCK : SHIPPING_DAYS_NOT_IN_STOCK);
    }

    private void updateStock(Order order) throws UnknownItemIdException {
        order.getOrderLines().forEach(orderLine -> {
            Item item = itemRepository.getById(orderLine.getItemId());
            item.setAmountInStock(item.getAmountInStock() - orderLine.getAmountInOrder());

            itemRepository.save(item);
        });
    }
}
