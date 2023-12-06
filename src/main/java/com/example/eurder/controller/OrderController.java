package com.example.eurder.controller;

import com.example.eurder.domain.Customer;
import com.example.eurder.dto.CreateOrderDto;
import com.example.eurder.dto.OrderDto;
import com.example.eurder.service.CustomerService;
import com.example.eurder.service.OrderService;
import jakarta.validation.Valid;
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
    public OrderDto createOrder(@RequestHeader String email, @RequestHeader String password, @Valid @RequestBody CreateOrderDto createOrderDto) {
        Customer customer = customerService.authenticate(email, password);

        return orderService.placeOrder(customer, createOrderDto);
    }
}
