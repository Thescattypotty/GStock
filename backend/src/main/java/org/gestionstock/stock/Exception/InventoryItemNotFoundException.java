package org.gestionstock.stock.Exception;

public class InventoryItemNotFoundException extends RuntimeException {
    public InventoryItemNotFoundException() {
        super("Inventory Item Not Found");
    }
    
}
