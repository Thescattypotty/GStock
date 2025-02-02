package org.gestionstock.stock.Payload.Mapper;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import org.gestionstock.stock.Entity.InventoryItem;
import org.gestionstock.stock.Payload.Request.InventoryItemRequest;
import org.gestionstock.stock.Payload.Response.InventoryItemResponse;
import org.springframework.stereotype.Service;


@Service(value = "InventoryItemMapper")
public class InventoryItemMapper {
    
    public static InventoryItem toInventoryItem(InventoryItemRequest inventoryItemRequest){
        return InventoryItem.builder()
            .name(inventoryItemRequest.name())
            .description(inventoryItemRequest.description())
            .quantity(inventoryItemRequest.quantity())
            .costPrice(BigDecimal.valueOf(inventoryItemRequest.costPrice()))
            .tva(BigDecimal.valueOf(inventoryItemRequest.tva()))
            .build();
    }

    public static InventoryItem toInventoryItem(InventoryItem inventoryItem, InventoryItemRequest inventoryItemRequest){
        inventoryItem.setName(inventoryItemRequest.name());
        inventoryItem.setDescription(inventoryItemRequest.description());
        inventoryItem.setQuantity(inventoryItemRequest.quantity());
        inventoryItem.setCostPrice(BigDecimal.valueOf(inventoryItemRequest.costPrice()));
        inventoryItem.setTva(BigDecimal.valueOf(inventoryItemRequest.tva()));
        return inventoryItem;
    }

    public static InventoryItemResponse fromInventoryItem(InventoryItem inventoryItem){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new InventoryItemResponse(
            inventoryItem.getId().toString(),
            inventoryItem.getName(),
            inventoryItem.getDescription(),
            inventoryItem.getQuantity(),
            inventoryItem.getCostPrice().doubleValue(),
            inventoryItem.getTva().doubleValue(),
            ItemCategoryMapper.fromItemCategory(inventoryItem.getCategory()),
            inventoryItem.getCreatedAt().format(formatter),
            inventoryItem.getUpdatedAt().format(formatter),
            inventoryItem.getCreatedBy(),
            inventoryItem.getUpdatedBy()
        );
    }
}
