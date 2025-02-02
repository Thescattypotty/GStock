package org.gestionstock.stock.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.gestionstock.stock.Entity.InventoryItem;
import org.gestionstock.stock.EntityRepository.InventoryItemRepository;
import org.gestionstock.stock.EntityRepository.ItemCategoryRepository;
import org.gestionstock.stock.Exception.InventoryItemNotFoundException;
import org.gestionstock.stock.Exception.ItemCategoryNotFoundException;
import org.gestionstock.stock.IService.IInventoryItemService;
import org.gestionstock.stock.Payload.Mapper.InventoryItemMapper;
import org.gestionstock.stock.Payload.Request.InventoryItemRequest;
import org.gestionstock.stock.Payload.Response.InventoryItemResponse;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryItemService implements IInventoryItemService{
    
    private final InventoryItemRepository inventoryItemRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    @Override
    @Transactional
    public void createInventoryItem(InventoryItemRequest inventoryItemRequest) {
        log.info("Creating inventory item: {}", inventoryItemRequest);
        InventoryItem inventoryItem = InventoryItemMapper.toInventoryItem(inventoryItemRequest);
        log.info("Inventory item: {}", inventoryItem);
        inventoryItem.setCategory(
            itemCategoryRepository.findById(UUID.fromString(inventoryItemRequest.categoryId()))
                .orElseThrow(() -> new ItemCategoryNotFoundException())
        ); 
        inventoryItemRepository.save(inventoryItem);
        log.info("Inventory item created successfully");
    }

    @Override
    @Transactional
    public void updateInventoryItem(String id, InventoryItemRequest inventoryItemRequest) {
        log.info("Updating inventory item: {}", inventoryItemRequest);
        InventoryItem inventoryItem = inventoryItemRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new InventoryItemNotFoundException());
        log.info("Inventory item: {}", inventoryItem);
        inventoryItem = InventoryItemMapper.toInventoryItem(inventoryItem, inventoryItemRequest);
        inventoryItem.setCategory(
            itemCategoryRepository.findById(UUID.fromString(inventoryItemRequest.categoryId()))
                .orElseThrow(() -> new ItemCategoryNotFoundException())
        );
        inventoryItemRepository.save(inventoryItem);
        log.info("Inventory item updated successfully");
    }

    @Override
    @Transactional
    public void deleteInventoryItem(String id) {
        log.info("Deleting inventory item: {}", id);
        if(!inventoryItemRepository.existsById(UUID.fromString(id))){
            log.info("Inventory item not found: {}", id);
            throw new InventoryItemNotFoundException();
        }
        inventoryItemRepository.deleteById(UUID.fromString(id));
        log.info("Inventory item deleted successfully");
    }

    @Override
    public InventoryItemResponse getInventoryItem(String id) {
        log.info("Getting inventory item: {}", id);
        return inventoryItemRepository.findById(UUID.fromString(id))
            .map(InventoryItemMapper::fromInventoryItem)
            .orElseThrow(() -> new InventoryItemNotFoundException());
    }

    @Override
    public List<InventoryItemResponse> getAllInventoryItems() {
        log.info("Getting all inventory items");
        return inventoryItemRepository.findAll()
            .stream()
            .map(InventoryItemMapper::fromInventoryItem)
            .collect(Collectors.toList());
    }
}
