package com.example.eurder.repository;

import com.example.eurder.domain.Item;
import com.example.eurder.exception.UnknownItemIdException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {
    private static Integer autoIncrementId = 0;
    private Map<Integer, Item> items = new HashMap<>();

    public Item create(Item item) {
        autoIncrementId++;
        item.setId(autoIncrementId);

        return save(item);
    }

    public Item save(Item item) {
        items.put(item.getId(), item);

        return item;
    }

    public void truncate() {
        autoIncrementId = 0;
        items = new HashMap<>();
    }

    public Item getById(Integer id) {
        return items.values().stream().filter(item -> item.getId().equals(id)).findFirst().orElseThrow(UnknownItemIdException::new);
    }
}
