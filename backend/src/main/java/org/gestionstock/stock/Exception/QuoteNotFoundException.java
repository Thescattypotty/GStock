package org.gestionstock.stock.Exception;

public class QuoteNotFoundException extends RuntimeException {
    public QuoteNotFoundException() {
        super("Quote not found");
    }
    
}
