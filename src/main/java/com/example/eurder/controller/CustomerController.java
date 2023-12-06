package com.example.eurder.controller;

import com.example.eurder.dto.CreateCustomerDto;
import com.example.eurder.dto.CustomerDto;
import com.example.eurder.service.AdminService;
import com.example.eurder.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final AdminService adminService;

    public CustomerController(CustomerService customerService, AdminService adminService) {
        this.customerService = customerService;
        this.adminService = adminService;
    }

    @PostMapping
    public CustomerDto createCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) {
        return customerService.createCustomer(createCustomerDto);
    }

//    @GetMapping
//    public CustomerDto[] getCustomers(@RequestHeader String email, @RequestHeader String password) {
//        return customerService.createCustomer(createCustomerDto);
//    }

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@RequestHeader String email, @RequestHeader String password, @PathVariable Integer id) {
        adminService.authenticate(email, password);

        return customerService.getCustomer(id);
    }
}
