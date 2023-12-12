package com.switchfully.eurder.controller;

import com.switchfully.eurder.domain.Currency;
import com.switchfully.eurder.domain.Price;
import com.switchfully.eurder.dto.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Test
    void givenCreateOrderDto_whenPlaceOrder_thenGetOrderDto() {
        // GIVEN
        String email = "e@mail";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String phoneNumber = "phoneNumber";
        String address = "address";
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(email, password, firstName, lastName, phoneNumber, address);

        CustomerDto customerDto =
                RestAssured
                        .given()
                        .body(createCustomerDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/customers")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(CustomerDto.class);

        String name = "e@mail";
        String description = "password";
        Price price = new Price(10.0, Currency.EUR);
        Integer amountInStock = 10;
        CreateItemDto createItemDto = new CreateItemDto(name, description, price, amountInStock);

        ItemDto itemDto =
                RestAssured
                        .given()
                        .body(createItemDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("email", "admin@eurder.com")
                        .header("password", "admin")
                        .when()
                        .port(port)
                        .post("/items")
                        .then()
                        .extract()
                        .as(ItemDto.class);

        Integer amountInOrder = 1;
        LocalDate orderDate = LocalDate.now();
        List<CreateOrderLineDto> createOrderLineDtos = new ArrayList<>(){{
            add(new CreateOrderLineDto(itemDto.getId(), amountInOrder));
        }};
        CreateOrderDto createOrderDto = new CreateOrderDto(createOrderLineDtos, orderDate);

        // WHEN
        OrderDto actual =
                RestAssured
                        .given()
                        .body(createOrderDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("email", email)
                        .header("password", password)
                        .when()
                        .port(port)
                        .post("/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(OrderDto.class);

        assertThat(actual).isInstanceOf(OrderDto.class);
        assertThat(actual.getCustomerId()).isEqualTo(customerDto.getId());
        assertThat(actual.getCustomerAddress()).isEqualTo(customerDto.getAddress());
        assertThat(actual.getOrderLineDtos()).hasSize(1);
        assertThat(actual.getOrderDate()).isEqualTo(orderDate);
    }
}