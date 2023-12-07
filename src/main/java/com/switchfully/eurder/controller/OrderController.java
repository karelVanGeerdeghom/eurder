package com.switchfully.eurder.controller;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.dto.CreateOrderDto;
import com.switchfully.eurder.dto.OrderDto;
import com.switchfully.eurder.service.CustomerService;
import com.switchfully.eurder.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {
    private CustomerService customerService;
    private OrderService orderService;

    public OrderController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDto createOrder(@RequestHeader String email, @RequestHeader String password, @RequestBody CreateOrderDto createOrderDto) {
        Customer customer = customerService.authenticate(email, password);

        return orderService.placeOrder(customer, createOrderDto);
    }
}
