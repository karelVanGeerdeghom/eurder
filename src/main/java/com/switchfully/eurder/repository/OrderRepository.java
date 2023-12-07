package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.exception.UnknownOrderIdException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {
    private static Integer autoIncrementId = 0;
    private Map<Integer, Order> orders = new HashMap<>();

    public Order create(Order order) {
        autoIncrementId++;
        order.setId(autoIncrementId);

        return save(order);
    }

    public Order save(Order order) {
        orders.put(order.getId(), order);

        return order;
    }

    public void truncate() {
        autoIncrementId = 0;
        orders = new HashMap<>();
    }

    public Order getById(Integer id) throws UnknownOrderIdException {
        return orders.values().stream().filter(order -> order.getId().equals(id)).findFirst().orElseThrow(UnknownOrderIdException::new);
    }

    public List<Order> getAllOrdersByCustomer(Customer customer) {
        return orders.values().stream().filter(order -> order.getCustomerId().equals(customer.getId())).collect(Collectors.toList());
    }

    public List<Order> getAllOrdersShippingOnDate(LocalDate shippingDate) {
        return orders.values().stream()
                .filter(order -> order.getOrderLines().stream()
                        .anyMatch(orderLine -> orderLine.getShippingDate().isEqual(shippingDate)))
                .collect(Collectors.toList());
    }
}
