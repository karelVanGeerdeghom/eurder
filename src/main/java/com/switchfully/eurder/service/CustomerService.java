package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.dto.CreateCustomerDto;
import com.switchfully.eurder.dto.CustomerDto;
import com.switchfully.eurder.exception.UnknownCustomerEmailException;
import com.switchfully.eurder.exception.WrongPasswordException;
import com.switchfully.eurder.mapper.CustomerMapper;
import com.switchfully.eurder.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
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

    private Customer getByEmail(String email) {
        return customerRepository.getByEmail(email);
    }



    public CustomerDto createCustomer(CreateCustomerDto createCustomerDto) {
        createCustomerDto.setPassword(bCryptPasswordEncoder.encode(createCustomerDto.getPassword()));

        Customer customer = customerRepository.create(customerMapper.createCustomerDtoToCustomer(createCustomerDto));
        CustomerDto customerDto = customerMapper.customerToCustomerDto(customer);

        return customerDto;
    }

    public CustomerDto getById(Integer id) {
        Customer customer = customerRepository.getById(id);
        CustomerDto customerDto = customerMapper.customerToCustomerDto(customer);

        return customerDto;
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.getAll().stream().map(customerMapper::customerToCustomerDto).collect(Collectors.toList());
    }
}
