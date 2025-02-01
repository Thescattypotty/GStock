package org.gestionstock.stock.Payload.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemCategoryRequest(
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    String name
) {
    
}
