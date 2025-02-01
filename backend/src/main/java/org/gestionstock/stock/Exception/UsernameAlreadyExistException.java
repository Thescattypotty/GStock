package org.gestionstock.stock.Exception;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException() {
        super("Username already exist");
    }
    
}
