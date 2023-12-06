package com.example.eurder.mapper;

import com.example.eurder.domain.Item;
import com.example.eurder.dto.CreateItemDto;
import com.example.eurder.dto.ItemDto;
import com.example.eurder.dto.UpdateItemDto;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemDto itemToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getAmountInStock());
    }

    public Item itemDtoToItem(ItemDto itemDto) {
        Item item = new Item(itemDto.getName(), itemDto.getDescription(), itemDto.getPrice(), itemDto.getAmountInStock());
        item.setId(itemDto.getId());

        return item;
    }

    public Item createItemDtoToItem(CreateItemDto createItemDto) {
        return new Item(createItemDto.getName(), createItemDto.getDescription(), createItemDto.getPrice(), createItemDto.getAmountInStock());
    }

    public Item updateItemDtoToItem(Item item, UpdateItemDto updateItemDto) {
        item.setName(updateItemDto.getName());
        item.setDescription(updateItemDto.getDescription());
        item.setPrice(updateItemDto.getPrice());
        item.setAmountInStock(updateItemDto.getAmountInStock());

        return item;
    }
}
