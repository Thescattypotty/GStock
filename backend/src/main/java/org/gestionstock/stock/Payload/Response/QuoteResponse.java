package org.gestionstock.stock.Payload.Response;

import java.util.List;

public record QuoteResponse(
    String id,
    String quoteName,
    String description,
    CompanyResponse company,
    ContactResponse contact,
    List<ProductQuoteResponse> productQuote,
    String createdAt,
    String updatedAt,
    String createdBy,
    String updatedBy
) {
    
}
