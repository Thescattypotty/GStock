package org.gestionstock.stock.Payload.Request;

import java.util.List;

public record QuoteRequest(
    String quoteName,
    String companyId,
    String contactId,
    String description,
    List<ProductQuoteRequest> productQuoteRequest
) {
    
}
