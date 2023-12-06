package com.example.eurder.repository;

import com.example.eurder.domain.Order;
import com.example.eurder.exception.UnknownOrderIdException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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

    public Order getById(Integer id) {
        return orders.values().stream().filter(order -> order.getId().equals(id)).findFirst().orElseThrow(UnknownOrderIdException::new);
    }
}
