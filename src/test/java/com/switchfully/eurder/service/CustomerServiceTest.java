package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.dto.CreateCustomerDto;
import com.switchfully.eurder.dto.CustomerDto;
import com.switchfully.eurder.exception.UnknownCustomerEmailException;
import com.switchfully.eurder.exception.UnknownCustomerIdException;
import com.switchfully.eurder.exception.WrongPasswordException;
import com.switchfully.eurder.mapper.CustomerMapper;
import com.switchfully.eurder.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerServiceTest {
    private Customer customer;
    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        customer = new Customer("customer@eurder.com", bCryptPasswordEncoder.encode("customer"), "firstName", "lastName", "phoneNumber", "address");

        customerMapper = new CustomerMapper();

        customerRepository = new CustomerRepository();
        customerRepository.truncate();
        customerRepository.create(customer);

        customerService = new CustomerService(customerMapper, customerRepository);
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
    void givenCreateCustomerDto_whenCreateCustomer_thenGetCustomerDto() {
        // GIVEN
        String email = "firstName.lastName@mail.com";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String phoneNumber = "phoneNumber";
        String address = "address";
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(email, password, firstName, lastName, phoneNumber, address);

        // WHEN
        CustomerDto actual = customerService.createCustomer(createCustomerDto);

        // THEN
        assertThat(actual).isInstanceOf(CustomerDto.class);
        assertThat(actual.getId()).isEqualTo(2);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEqualTo(lastName);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getAddress()).isEqualTo(address);
    }

    @Test
    void givenExistingId_whenGetCustomerById_thenGetCustomerWithGivenId() {
        // GIVEN
        Integer id = 1;

        // WHEN
        CustomerDto actual = customerService.getById(id);

        // THEN
        assertThat(actual).isInstanceOf(CustomerDto.class);
        assertThat(actual.getId()).isEqualTo(id);
    }

    @Test
    void givenWrongId_whenGetCustomerById_thenThrowUnknownCustomerIdException() {
        // GIVEN
        Integer id = 0;

        // WHEN + THEN
        assertThatThrownBy(() -> customerService.getById(id)).isInstanceOf(UnknownCustomerIdException.class);
    }

    @Test
    void whenGetAllCustomers_thenGetAllCustomerDtos() {
        // WHEN
        List<CustomerDto> actual = customerService.getAllCustomers();

        // THEN
        assertThat(actual).containsExactly(customerMapper.customerToCustomerDto(customer));
    }
}