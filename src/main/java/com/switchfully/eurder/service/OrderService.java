package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.domain.OrderLine;
import com.switchfully.eurder.dto.CreateOrderDto;
import com.switchfully.eurder.dto.CreateOrderLineDto;
import com.switchfully.eurder.dto.DuplicateOrderDto;
import com.switchfully.eurder.dto.OrderDto;
import com.switchfully.eurder.exception.*;
import com.switchfully.eurder.mapper.OrderLineMapper;
import com.switchfully.eurder.mapper.OrderMapper;
import com.switchfully.eurder.repository.ItemRepository;
import com.switchfully.eurder.repository.OrderRepository;
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



    public OrderDto placeOrder(Customer customer, CreateOrderDto createOrderDto) throws NoOrderLinesException, InvalidAmountInOrderInOrderLineException, UnknownItemIdException {
        if (createOrderDto.getCreateOrderLineDtos().isEmpty()) {
            throw new NoOrderLinesException();
        }

        Order order = createOrder(customer, createOrderDto);
        updateStock(order);

        return orderMapper.orderToOrderDto(order);
    }

    public OrderDto duplicateOrder(Customer customer, Integer id, DuplicateOrderDto duplicateOrderDto) {
        CreateOrderDto createOrderDto = orderMapper.duplicateOrderToCreateOrderDto(orderRepository.getById(id), duplicateOrderDto);

        Order order = createOrder(customer, createOrderDto);
        updateStock(order);

        return orderMapper.orderToOrderDto(order);
    }

    private Order createOrder(Customer customer, CreateOrderDto createOrderDto) throws InvalidAmountInOrderInOrderLineException, UnknownItemIdException {
        List<OrderLine> orderLines = createOrderLines(createOrderDto);

        return orderRepository.create(new Order(customer.getId(), customer.getAddress(), orderLines, createOrderDto.getOrderDate()));
    }

    private List<OrderLine> createOrderLines(CreateOrderDto createOrderDto) throws InvalidAmountInOrderInOrderLineException, UnknownItemIdException {
        return createOrderDto.getCreateOrderLineDtos().stream()
                .map(createOrderLineDto -> createOrderLine(createOrderDto, createOrderLineDto))
                .toList();
    }

    private OrderLine createOrderLine(CreateOrderDto createOrderDto, CreateOrderLineDto createOrderLineDto) throws InvalidAmountInOrderInOrderLineException, UnknownItemIdException {
        if (createOrderLineDto.getAmountInOrder() < 1) {
            throw new InvalidAmountInOrderInOrderLineException();
        }

        Item item = itemRepository.getById(createOrderLineDto.getItemId());
        LocalDate shippingDate = getShippingDate(item, createOrderLineDto.getAmountInOrder(), createOrderDto.getOrderDate());
        OrderLine orderLine = orderLineMapper.createOrderLineDtoToOrderLine(item, createOrderLineDto, shippingDate);

        return orderLine;
    }

    private LocalDate getShippingDate(Item item, Integer amountInOrder, LocalDate orderDate) {
        return orderDate.plusDays(item.getAmountInStock() >= amountInOrder ? SHIPPING_DAYS_IN_STOCK : SHIPPING_DAYS_NOT_IN_STOCK);
    }

    private void updateStock(Order order) throws UnknownItemIdException {
        order.getOrderLines().forEach(orderLine -> {
            Item item = itemRepository.getById(orderLine.getItemId());
            item.setAmountInStock(item.getAmountInStock() - orderLine.getAmountInOrder());

            itemRepository.save(item);
        });
    }



    public List<OrderDto> getAllOrdersForCustomer(Customer customer) {
        return orderRepository.getAllOrdersByCustomer(customer).stream().map(order -> orderMapper.orderToOrderDto(order)).collect(Collectors.toList());
    }

    public void validateOrderByIdForCustomer(Customer customer, Integer id) throws UnknownOrderIdException, OrderIsNotForCustomerException {
        Order order = orderRepository.getById(id);
        if (!order.getCustomerId().equals(customer.getId())) {
            throw new OrderIsNotForCustomerException();
        }
    }

    public OrderDto getById(Integer id) {
        return orderMapper.orderToOrderDto(orderRepository.getById(id));
    }

    public List<OrderDto> getAllOrdersShippingOnDate(LocalDate shippingDate) {
        return orderRepository.getAllOrdersShippingOnDate(shippingDate).stream()
                .map(order -> orderMapper.orderToOrderDto(order))
                .map(orderDto -> new OrderDto(orderDto.getId(), orderDto.getCustomerId(), orderDto.getCustomerAddress(), orderDto.getOrderLineDtos().stream().filter(orderLineDto -> orderLineDto.getShippingDate().equals(shippingDate)).collect(Collectors.toList()), orderDto.getOrderDate()))
                .collect(Collectors.toList());
    }
}
