package org.gestionstock.stock.Exception;

public class ProductQuoteNotFoundException extends RuntimeException {
    public ProductQuoteNotFoundException() {
        super("Product Quote not found");
    }
    
}
