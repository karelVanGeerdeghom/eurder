package com.example.eurder.service;

import com.example.eurder.domain.Customer;
import com.example.eurder.dto.CustomerDto;
import com.example.eurder.exception.UnknownCustomerEmailException;
import com.example.eurder.exception.UnknownCustomerIdException;
import com.example.eurder.exception.WrongPasswordException;
import com.example.eurder.mapper.CustomerMapper;
import com.example.eurder.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerServiceTest {
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        CustomerRepository customerRepository = new CustomerRepository();
        customerRepository.truncate();
        Customer customer = new Customer("customer@eurder.com", bCryptPasswordEncoder.encode("customer"), "firstName", "lastName", "phoneNumber", "address");
        customerRepository.create(customer);

        customerService = new CustomerService(new CustomerMapper(), customerRepository);
    }

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

    @Test
    void givenExistingId_whenGetCustomerById_thenGetCustomerWithGivenId() {
        // GIVEN
        Integer id = 1;

        // WHEN
        CustomerDto actual = customerService.getCustomer(id);

        // THEN
        assertThat(actual).isInstanceOf(CustomerDto.class);
    }

    @Test
    void givenWrongId_whenGetCustomerById_thenThrowUnknownCustomerIdException() {
        // GIVEN
        Integer id = 0;

        // WHEN + THEN
        assertThatThrownBy(() -> customerService.getCustomer(id)).isInstanceOf(UnknownCustomerIdException.class);
    }
}