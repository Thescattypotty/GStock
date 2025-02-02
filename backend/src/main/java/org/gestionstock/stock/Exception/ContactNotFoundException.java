package org.gestionstock.stock.Exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException() {
        super("Contact not found");
    }
    
}
