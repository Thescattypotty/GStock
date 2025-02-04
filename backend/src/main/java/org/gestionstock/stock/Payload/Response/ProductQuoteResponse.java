package org.gestionstock.stock.Payload.Response;

public record ProductQuoteResponse(
    String id,
    InventoryItemResponse item,
    Long quantity,
    Double discount,
    Double margeDeGain,
    Double priceHT,
    Double priceTTC
) {
    
}
