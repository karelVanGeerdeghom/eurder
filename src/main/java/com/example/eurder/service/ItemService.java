package com.example.eurder.service;

import com.example.eurder.domain.Item;
import com.example.eurder.dto.CreateItemDto;
import com.example.eurder.dto.ItemDto;
import com.example.eurder.mapper.ItemMapper;
import com.example.eurder.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private ItemMapper itemMapper;
    private ItemRepository itemRepository;

    public ItemService(ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    public ItemDto createItem(CreateItemDto createItemDto) {
        Item item = itemRepository.create(itemMapper.createItemDtoToItem(createItemDto));
        ItemDto itemDto = itemMapper.itemToItemDto(item);

        return itemDto;
    }

    public ItemDto getItem(Integer id) {
        Item item = itemRepository.getById(id);
        ItemDto itemDto = itemMapper.itemToItemDto(item);

        return itemDto;
    }
}
