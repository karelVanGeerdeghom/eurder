package com.example.eurder.repository;

import com.example.eurder.domain.Admin;
import com.example.eurder.domain.Customer;
import com.example.eurder.exception.UnknownCustomerEmailException;
import com.example.eurder.exception.UnknownCustomerIdException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomerRepository {
    private static Integer autoIncrementId = 0;
    private Map<Integer, Customer> customers = new HashMap<>();

    public CustomerRepository() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        customers.put(0, new Customer("customer@eurder.com", bCryptPasswordEncoder.encode("customer"), "firstName", "lastName", "phoneNumber", "address"));
    }

    public Customer create(Customer customer) {
        autoIncrementId++;
        customer.setId(autoIncrementId);

        return save(customer);
    }

    private Customer save(Customer customer) {
        customers.put(customer.getId(), customer);

        return customer;
    }

    public void truncate() {
        autoIncrementId = 0;
        customers = new HashMap<>();
    }

    public Customer getById(Integer id) {
        return customers.values().stream().filter(customer -> customer.getId().equals(id)).findFirst().orElseThrow(UnknownCustomerIdException::new);
    }

    public Customer getByEmail(String email) {
        return customers.values().stream().filter(customer -> customer.getEmail().equals(email)).findFirst().orElseThrow(UnknownCustomerEmailException::new);
    }
}
