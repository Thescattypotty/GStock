package org.gestionstock.stock.Payload.Request;

public record ProductQuoteRequest(
    String itemId,
    Long quantity,
    Double margeDeGain,
    Double discount
) {
    
}
