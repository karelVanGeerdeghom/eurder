package com.example.eurder.mapper;

import com.example.eurder.domain.Customer;
import com.example.eurder.dto.CreateCustomerDto;
import com.example.eurder.dto.CustomerDto;
import com.example.eurder.dto.UpdateCustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDto customerToCustomerDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhoneNumber(), customer.getAddress());
    }

    public Customer createCustomerDtoToCustomer(CreateCustomerDto createCustomerDto) {
        return new Customer(createCustomerDto.getFirstName(), createCustomerDto.getLastName(), createCustomerDto.getEmail(), createCustomerDto.getPassword(), createCustomerDto.getPhoneNumber(), createCustomerDto.getAddress());
    }

    public Customer updateCustomerDtoToCustomer(UpdateCustomerDto updateCustomerDto) {
        return new Customer(updateCustomerDto.getFirstName(), updateCustomerDto.getLastName(), updateCustomerDto.getEmail(), updateCustomerDto.getPassword(), updateCustomerDto.getPhoneNumber(), updateCustomerDto.getAddress());
    }
}
