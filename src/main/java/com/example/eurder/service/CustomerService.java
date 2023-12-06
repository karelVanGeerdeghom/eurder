package com.example.eurder.service;

import com.example.eurder.domain.Customer;
import com.example.eurder.dto.CreateCustomerDto;
import com.example.eurder.dto.CustomerDto;
import com.example.eurder.exception.UnknownCustomerEmailException;
import com.example.eurder.exception.WrongPasswordException;
import com.example.eurder.mapper.CustomerMapper;
import com.example.eurder.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;

    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public Customer authenticate(String email, String password) throws UnknownCustomerEmailException, WrongPasswordException {
        return validatePassword(getByEmail(email), password);
    }

    private Customer validatePassword(Customer customer, String password) throws WrongPasswordException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(password, customer.getPassword())) {
            throw new WrongPasswordException();
        }

        return customer;
    }

    public Customer getByEmail(String email) {
        return customerRepository.getByEmail(email);
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

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.getAll().stream().map(customerMapper::customerToCustomerDto).collect(Collectors.toList());
    }
}
