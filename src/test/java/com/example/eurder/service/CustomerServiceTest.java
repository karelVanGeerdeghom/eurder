package com.example.eurder.service;

import com.example.eurder.domain.Customer;
import com.example.eurder.exception.UnknownCustomerEmailException;
import com.example.eurder.exception.WrongPasswordException;
import com.example.eurder.mapper.CustomerMapper;
import com.example.eurder.repository.CustomerRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerServiceTest {
    private CustomerService customerService = new CustomerService(new CustomerMapper(), new CustomerRepository());

    @Test
    void givenRightEmailAndRightPassword_whenAuthenticate_thenGetAuthenticatedCustomer() {
        // GIVEN
        String email = "customer@eurder.com";
        String password = "customer";

        // WHEN
        Customer actual = customerService.authenticate(email, password);

        // THEN
        assertThat(actual).isInstanceOf(Customer.class);
    }

    @Test
    void givenRightEmailAndWrongPassword_whenAuthenticate_thenThrowWrongPasswordException() {
        // GIVEN
        String email = "customer@eurder.com";
        String password = "password";

        // WHEN + THEN
        assertThatThrownBy(() -> customerService.authenticate(email, password)).isInstanceOf(WrongPasswordException.class);
    }

    @Test
    void givenWrongEmailAndRightPassword_whenAuthenticate_thenThrowUnknownCustomerEmailException() {
        // GIVEN
        String email = "firstName.lastName@mail.com";
        String password = "customer";

        // WHEN + THEN
        assertThatThrownBy(() -> customerService.authenticate(email, password)).isInstanceOf(UnknownCustomerEmailException.class);
    }

    @Test
    void givenWrongEmailAndWrongPassword_whenAuthenticate_thenThrowUnknownCustomerEmailException() {
        // GIVEN
        String email = "firstName.lastName@mail.com";
        String password = "password";

        // WHEN + THEN
        assertThatThrownBy(() -> customerService.authenticate(email, password)).isInstanceOf(UnknownCustomerEmailException.class);
    }
}