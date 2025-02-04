package org.gestionstock.stock.IService;

import java.util.List;

import org.gestionstock.stock.Payload.Request.ProductQuoteRequest;
import org.gestionstock.stock.Payload.Request.QuoteRequest;
import org.gestionstock.stock.Payload.Response.QuoteResponse;

public interface IQuoteService {
    void createQuote(QuoteRequest quoteRequest);
    void updateQuote(String id, QuoteRequest quoteRequest);
    void deleteQuote(String id);
    void updateProductQuote(String id, ProductQuoteRequest productQuoteRequest);
    void deleteProductQuote(String id);
    QuoteResponse getQuote(String id);
    List<QuoteResponse> getQuotes();
}
