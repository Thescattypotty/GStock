package org.gestionstock.stock.Payload.Response;


public record InventoryItemResponse(
    String id,
    String name,
    String description,
    Long quantity,
    Double costPrice,
    Double tva,
    ItemCategoryResponse category,
    String createdAt,
    String updatedAt,
    String createdBy,
    String updatedBy
) {
    
}
