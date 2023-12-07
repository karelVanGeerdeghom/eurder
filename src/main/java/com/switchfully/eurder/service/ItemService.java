package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.StockIndicator;
import com.switchfully.eurder.dto.CreateItemDto;
import com.switchfully.eurder.dto.ItemDto;
import com.switchfully.eurder.dto.ItemStockIndicatorDto;
import com.switchfully.eurder.dto.UpdateItemDto;
import com.switchfully.eurder.exception.UnknownItemIdException;
import com.switchfully.eurder.mapper.ItemMapper;
import com.switchfully.eurder.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public ItemService(ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }



    public ItemDto createItem(CreateItemDto createItemDto) {
        Item item = itemRepository.create(itemMapper.createItemDtoToItem(createItemDto));

        return itemMapper.itemToItemDto(item);
    }

    public ItemDto updateItem(Integer id, UpdateItemDto updateItemDto) throws UnknownItemIdException {
        Item item = itemRepository.save(itemMapper.updateItemDtoToItem(itemRepository.getById(id), updateItemDto));

        return itemMapper.itemToItemDto(item);
    }

    public ItemDto getById(Integer id) throws UnknownItemIdException {
        Item item = itemRepository.getById(id);

        return itemMapper.itemToItemDto(item);
    }

    public List<ItemDto> getAllItems() {
        return itemRepository.getAllItems().stream().map(itemMapper::itemToItemDto).collect(Collectors.toList());
    }

    public List<ItemStockIndicatorDto> getAllItemStockIndicators(StockIndicator stockIndicator) {
        return itemRepository.getAllItems().stream()
                .sorted(Comparator.comparing(Item::getAmountInStock))
                .map(itemMapper::itemToItemStockIndicatorDto)
                .filter(itemStockIndicator -> stockIndicator == null || itemStockIndicator.getStockIndicator() == stockIndicator)
                .collect(Collectors.toList());
    }
}
