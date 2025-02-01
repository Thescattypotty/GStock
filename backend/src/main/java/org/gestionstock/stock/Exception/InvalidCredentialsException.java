package org.gestionstock.stock.Exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super("Invalid Credentials");
    }
}
