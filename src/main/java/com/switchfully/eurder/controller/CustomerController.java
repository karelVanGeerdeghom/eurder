package com.switchfully.eurder.controller;

import com.switchfully.eurder.dto.CreateCustomerDto;
import com.switchfully.eurder.dto.CustomerDto;
import com.switchfully.eurder.service.AdminService;
import com.switchfully.eurder.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
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

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@RequestHeader String email, @RequestHeader String password, @PathVariable Integer id) {
        adminService.authenticate(email, password);

        return customerService.getById(id);
    }

    @GetMapping
    public List<CustomerDto> getCustomers(@RequestHeader String email, @RequestHeader String password) {
        adminService.authenticate(email, password);

        return customerService.getAllCustomers();
    }
}
