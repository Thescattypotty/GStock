package org.gestionstock.stock.IService;

import java.util.List;

import org.gestionstock.stock.Payload.Request.InventoryItemRequest;
import org.gestionstock.stock.Payload.Response.InventoryItemResponse;

public interface IInventoryItemService {
    void createInventoryItem(InventoryItemRequest inventoryItemRequest);
    void updateInventoryItem(String id , InventoryItemRequest inventoryItemRequest);
    void deleteInventoryItem(String id);
    List<InventoryItemResponse> getAllInventoryItems();
    InventoryItemResponse getInventoryItem(String id);
}
