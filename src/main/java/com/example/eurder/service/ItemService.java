package com.example.eurder.service;

import com.example.eurder.domain.Item;
import com.example.eurder.dto.CreateItemDto;
import com.example.eurder.dto.ItemDto;
import com.example.eurder.dto.UpdateItemDto;
import com.example.eurder.exception.UnknownItemIdException;
import com.example.eurder.mapper.ItemMapper;
import com.example.eurder.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public ItemDto getItem(Integer id) throws UnknownItemIdException {
        Item item = itemRepository.getById(id);
        ItemDto itemDto = itemMapper.itemToItemDto(item);

        return itemDto;
    }

    public ItemDto updateItem(Integer id, UpdateItemDto updateItemDto) throws UnknownItemIdException {
        Item item = itemRepository.save(itemMapper.updateItemDtoToItem(itemRepository.getById(id), updateItemDto));
        ItemDto itemDto = itemMapper.itemToItemDto(item);

        return itemDto;
    }
}
