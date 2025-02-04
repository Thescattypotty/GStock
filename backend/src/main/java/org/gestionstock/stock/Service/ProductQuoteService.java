package org.gestionstock.stock.Service;

import java.math.BigDecimal;

import org.gestionstock.stock.Entity.InventoryItem;
import org.gestionstock.stock.Entity.ProductQuote;
import org.gestionstock.stock.IService.IProductQuoteService;
import org.gestionstock.stock.Payload.Request.ProductQuoteRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductQuoteService implements IProductQuoteService{
    
    @Override
    public ProductQuote createProductQuote(ProductQuote productQuote) {
        BigDecimal prixHt = productQuote.getItem()
            .getCostPrice()
            .multiply(
                BigDecimal.valueOf(1).add(productQuote.getMargeDeGain())
                );
        BigDecimal prixTtc = prixHt.multiply(BigDecimal.valueOf(1)
            .add(productQuote.getItem().getTva()));
        prixHt = prixHt.multiply(BigDecimal.valueOf(1).subtract(productQuote.getDiscount()));
        prixTtc = prixTtc.multiply(BigDecimal.valueOf(1).subtract(productQuote.getDiscount()));
        productQuote.setPriceHT(prixHt);
        productQuote.setPriceTTC(prixTtc);
        return productQuote;
    }

    @Override
    public ProductQuote createProductQuote(ProductQuoteRequest productQuoteRequest, InventoryItem item) {
        ProductQuote productQuote = new ProductQuote();
        productQuote.setItem(item);
        productQuote.setQuantity(productQuoteRequest.quantity());
        productQuote.setDiscount(BigDecimal.valueOf(productQuoteRequest.discount()));
        productQuote.setMargeDeGain(BigDecimal.valueOf(productQuoteRequest.margeDeGain()));
        return createProductQuote(productQuote);
    }

    
}
