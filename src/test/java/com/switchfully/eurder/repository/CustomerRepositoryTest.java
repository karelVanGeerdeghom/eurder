package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.exception.UnknownCustomerEmailException;
import com.switchfully.eurder.exception.UnknownCustomerIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        Customer customer = new Customer(email, password, firstName, lastName, phoneNumber, address);

        // WHEN
        Customer actual = customerRepository.create(customer);

        // THEN
        assertThat(actual).isInstanceOf(Customer.class);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEqualTo(lastName);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getAddress()).isEqualTo(address);
    }

    @Test
    void givenExistingId_whenGetCustomerById_thenGetCustomerWithGivenId() {
        // GIVEN
        String email = "firstName.lastName@mail.com";
        String password = "password";

        customerRepository.create(new Customer(email, password, "firstName", "lastName", "phoneNumber", "address"));

        // WHEN
        Customer actual = customerRepository.getById(1);

        // THEN
        assertThat(actual).isInstanceOf(Customer.class);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPassword()).isEqualTo(password);
    }

    @Test
    void givenExistingEmail_whenGetCustomerByEmail_thenGetCustomerWithGivenEmail() {
        // GIVEN
        String email = "firstName.lastName@mail.com";
        String password = "password";

        customerRepository.create(new Customer(email, password, "firstName", "lastName", "phoneNumber", "address"));

        // WHEN
        Customer actual = customerRepository.getByEmail(email);

        // THEN
        assertThat(actual).isInstanceOf(Customer.class);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPassword()).isEqualTo(password);
    }

    @Test
    void whenGetAllCustomers_thenGetAllCustomers() {
        // WHEN
        List<Customer> actual = customerRepository.getAll();

        // THEN
        assertThat(actual).allSatisfy(customer -> assertThat(customer).isInstanceOf(Customer.class));
    }

    @Test
    void givenUnknownId_whenGetCustomerById_thenThrowUnknownCustomerIdException() {
        // GIVEN
        Integer id = 1;

        // WHEN + THEN
        assertThatThrownBy(() -> customerRepository.getById(id)).isInstanceOf(UnknownCustomerIdException.class);
    }

    @Test
    void givenUnknownEmail_whenGetCustomerByEmail_thenThrowUnknownCustomerEmailException() {
        // GIVEN
        String email = "firstName.lastName@eurder.com";

        // WHEN + THEN
        assertThatThrownBy(() -> customerRepository.getByEmail(email)).isInstanceOf(UnknownCustomerEmailException.class);
    }
}