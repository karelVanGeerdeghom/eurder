package com.switchfully.eurder.controller;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.dto.CreateOrderDto;
import com.switchfully.eurder.dto.OrderDto;
import com.switchfully.eurder.service.AdminService;
import com.switchfully.eurder.service.CustomerService;
import com.switchfully.eurder.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/orders")
public class OrderController {
    private AdminService adminService;
    private CustomerService customerService;
    private OrderService orderService;

    public OrderController(AdminService adminService, CustomerService customerService, OrderService orderService) {
        this.adminService = adminService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDto createOrder(@RequestHeader String email, @RequestHeader String password, @Valid @RequestBody CreateOrderDto createOrderDto) {
        Customer customer = customerService.authenticate(email, password);

        return orderService.placeOrder(customer, createOrderDto);
    }

    @GetMapping
    public List<OrderDto> getAllOrdersByCustomer(@RequestHeader String email, @RequestHeader String password) {
        Customer customer = customerService.authenticate(email, password);

        return orderService.getAllOrdersByCustomer(customer);
    }

    @GetMapping("/today")
    public List<OrderDto> getAllOrdersShippingToday(@RequestHeader String email, @RequestHeader String password) {
        adminService.authenticate(email, password);

        return orderService.getAllOrdersShippingToday();
    }
}
