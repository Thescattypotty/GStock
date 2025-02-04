package org.gestionstock.stock.IService;

import org.gestionstock.stock.Entity.InventoryItem;
import org.gestionstock.stock.Entity.ProductQuote;
import org.gestionstock.stock.Payload.Request.ProductQuoteRequest;

public interface IProductQuoteService {
    ProductQuote createProductQuote(ProductQuote productQuote);
    ProductQuote createProductQuote(ProductQuoteRequest productQuoteRequest, InventoryItem item);
}
