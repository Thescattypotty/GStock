package org.gestionstock.stock.Exception;

public class ItemCategoryNotFoundException extends RuntimeException {
    public ItemCategoryNotFoundException() {
        super("Item Category Not Found");
    }
    
}
