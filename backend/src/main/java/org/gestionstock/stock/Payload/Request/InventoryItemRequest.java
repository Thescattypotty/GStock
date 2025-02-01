package org.gestionstock.stock.Payload.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InventoryItemRequest(
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    String name,

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description is required")
    String description,

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be greater than 0")
    Long quantity,

    @NotNull(message = "Cost price is required")
    @Min(value = 0, message = "Cost price must be greater than 0")
    Double costPrice,

    @NotNull(message = "TVA is required")
    @Min(value = 0, message = "TVA must be greater than 0")
    @Max(value = 1, message = "TVA must be less than 1")
    Double tva,

    @NotNull(message = "Category is required")
    String categoryId
) {
    
}
