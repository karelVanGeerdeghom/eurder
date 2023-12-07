package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.exception.UnknownCustomerEmailException;
import com.switchfully.eurder.exception.UnknownCustomerIdException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {
    private static Integer autoIncrementId = 0;
    private Map<Integer, Customer> customers = new HashMap<>();

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

    public List<Customer> getAll() {
        return customers.values().stream().toList();
    }
}
