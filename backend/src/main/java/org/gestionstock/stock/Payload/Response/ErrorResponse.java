package org.gestionstock.stock.Payload.Response;

public record ErrorResponse(
    String status,
    String error,
    String message,
    String path
) {
    
}
