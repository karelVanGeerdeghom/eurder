package com.example.eurder.service;

import com.example.eurder.domain.Customer;
import com.example.eurder.dto.CreateCustomerDto;
import com.example.eurder.dto.CustomerDto;
import com.example.eurder.mapper.CustomerMapper;
import com.example.eurder.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;

    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public CustomerDto createCustomer(CreateCustomerDto createCustomerDto) {
        Customer customer = customerRepository.create(customerMapper.createCustomerDtoToCustomer(createCustomerDto));
        CustomerDto customerDto = customerMapper.customerToCustomerDto(customer);

        return customerDto;
    }

    public CustomerDto getCustomer(Integer id) {
        Customer customer = customerRepository.getById(id);
        CustomerDto customerDto = customerMapper.customerToCustomerDto(customer);

        return customerDto;
    }
}
