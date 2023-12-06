package com.example.eurder.repository;

import com.example.eurder.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRepositoryTest {
    private CustomerRepository customerRepository = new CustomerRepository();

    @BeforeEach
    void truncate() {
        customerRepository.truncate();
    }

    @Test
    void givenCustomer_whenCreateCustomer_thenGetCreatedCustomerWithIdOne() {
        // GIVEN
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "firstName.lastName@mail.com";
        String password = "password";
        String phoneNumber = "phoneNumber";
        String address = "address";

        Customer customer = new Customer(firstName, lastName, email, password, phoneNumber, address);

        // WHEN
        Customer actual = customerRepository.create(customer);

        // THEN
        assertThat(actual).isInstanceOf(Customer.class);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEqualTo(lastName);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getAddress()).isEqualTo(address);
    }

    @Test
    void givenId_whenGetCustomerById_thenGetCustomerWithGivenId() {
        // GIVEN
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "firstName.lastName@mail.com";
        String password = "password";
        String phoneNumber = "phoneNumber";
        String address = "address";

        customerRepository.create(new Customer(firstName, lastName, email, password, phoneNumber, address));

        // WHEN
        Customer actual = customerRepository.getById(1);

        // THEN
        assertThat(actual).isInstanceOf(Customer.class);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEqualTo(lastName);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getAddress()).isEqualTo(address);
    }
}