package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.dto.CreateItemDto;
import com.switchfully.eurder.dto.ItemDto;
import com.switchfully.eurder.dto.UpdateItemDto;
import com.switchfully.eurder.exception.UnknownItemIdException;
import com.switchfully.eurder.mapper.ItemMapper;
import com.switchfully.eurder.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public ItemDto updateItem(Integer id, UpdateItemDto updateItemDto) throws UnknownItemIdException {
        Item item = itemRepository.save(itemMapper.updateItemDtoToItem(itemRepository.getById(id), updateItemDto));
        ItemDto itemDto = itemMapper.itemToItemDto(item);

        return itemDto;
    }

    public ItemDto getById(Integer id) throws UnknownItemIdException {
        Item item = itemRepository.getById(id);
        ItemDto itemDto = itemMapper.itemToItemDto(item);

        return itemDto;
    }

    public List<ItemDto> getAllItems() {
        return itemRepository.getAllItems().stream().map(item -> itemMapper.itemToItemDto(item)).collect(Collectors.toList());
    }
}
