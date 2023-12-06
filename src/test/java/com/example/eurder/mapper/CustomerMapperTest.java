package com.example.eurder.mapper;

import com.example.eurder.domain.Customer;
import com.example.eurder.dto.CreateCustomerDto;
import com.example.eurder.dto.CustomerDto;
import com.example.eurder.dto.UpdateCustomerDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {
    private CustomerMapper customerMapper = new CustomerMapper();

    @Test
    void givenCustomer_whenMapCustomerToCustomerDto_thenGetCustomerDto() {
        // GIVEN
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "firstName.lastName@mail.com";
        String password = "password";
        String phoneNumber = "phoneNumber";
        String address = "address";

        Customer customer = new Customer(firstName, lastName, email, password, phoneNumber, address);

        // WHEN
        CustomerDto actual = customerMapper.customerToCustomerDto(customer);

        // THEN
        assertThat(actual).isInstanceOf(CustomerDto.class);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEqualTo(lastName);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getAddress()).isEqualTo(address);
    }

    @Test
    void givenCreateCustomerDto_whenMapCreateCustomerDtoToCustomer_thenGetCustomer() {
        // GIVEN
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "firstName.lastName@mail.com";
        String password = "password";
        String phoneNumber = "phoneNumber";
        String address = "address";

        CreateCustomerDto createCustomerDto = new CreateCustomerDto(firstName, lastName, email, password, phoneNumber, address);

        // WHEN
        Customer actual = customerMapper.createCustomerDtoToCustomer(createCustomerDto);

        // THEN
        assertThat(actual).isInstanceOf(Customer.class);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEqualTo(lastName);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getAddress()).isEqualTo(address);
    }

    @Test
    void givenUpdateCustomerDto_whenMapUpdateCustomerDtoToCustomer_thenGetCustomer() {
        // GIVEN
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "firstName.lastName@mail.com";
        String password = "password";
        String phoneNumber = "phoneNumber";
        String address = "address";

        UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto(firstName, lastName, email, password, phoneNumber, address);

        // WHEN
        Customer actual = customerMapper.updateCustomerDtoToCustomer(updateCustomerDto);

        // THEN
        assertThat(actual).isInstanceOf(Customer.class);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEqualTo(lastName);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getAddress()).isEqualTo(address);
    }
}