package org.gestionstock.stock.Payload.Response;

public record ItemCategoryResponse(
    String id,
    String name,
    String createdAt,
    String updatedAt,
    String createdBy,
    String updatedBy
) {
    
}
