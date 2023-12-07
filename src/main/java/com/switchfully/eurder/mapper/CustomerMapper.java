package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.dto.CreateCustomerDto;
import com.switchfully.eurder.dto.CustomerDto;
import com.switchfully.eurder.dto.UpdateCustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDto customerToCustomerDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getEmail(), customer.getFirstName(), customer.getLastName(), customer.getPhoneNumber(), customer.getAddress());
    }

    public Customer createCustomerDtoToCustomer(CreateCustomerDto createCustomerDto) {
        return new Customer(createCustomerDto.getEmail(), createCustomerDto.getPassword(), createCustomerDto.getFirstName(), createCustomerDto.getLastName(), createCustomerDto.getPhoneNumber(), createCustomerDto.getAddress());
    }

    public Customer updateCustomerDtoToCustomer(UpdateCustomerDto updateCustomerDto) {
        return new Customer(updateCustomerDto.getEmail(), updateCustomerDto.getPassword(), updateCustomerDto.getFirstName(), updateCustomerDto.getLastName(), updateCustomerDto.getPhoneNumber(), updateCustomerDto.getAddress());
    }
}
