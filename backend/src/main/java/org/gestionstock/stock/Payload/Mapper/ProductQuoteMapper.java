package org.gestionstock.stock.Payload.Mapper;

import java.math.BigDecimal;

import org.gestionstock.stock.Entity.InventoryItem;
import org.gestionstock.stock.Entity.ProductQuote;
import org.gestionstock.stock.Payload.Request.ProductQuoteRequest;
import org.gestionstock.stock.Payload.Response.ProductQuoteResponse;

public class ProductQuoteMapper {
    
    public static ProductQuote toProductQuote(ProductQuoteRequest productQuoteRequest,InventoryItem item){
        return ProductQuote.builder()
            .item(item)
            .quantity(productQuoteRequest.quantity())
            .margeDeGain(BigDecimal.valueOf(productQuoteRequest.margeDeGain()))
            .discount(BigDecimal.valueOf(productQuoteRequest.discount()))
            .build();
    }

    public static ProductQuoteResponse fromProductQuote(ProductQuote productQuote){
        return new ProductQuoteResponse(
            productQuote.getId().toString(),
            InventoryItemMapper.fromInventoryItem(productQuote.getItem()),
            productQuote.getQuantity(),
            productQuote.getDiscount().doubleValue(),
            productQuote.getMargeDeGain().doubleValue(),
            productQuote.getPriceHT().doubleValue(),
            productQuote.getPriceTTC().doubleValue()
        );
    }
}
