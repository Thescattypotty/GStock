package org.gestionstock.stock.ExceptionHandler;

import org.gestionstock.stock.Exception.CompanyNotFoundException;
import org.gestionstock.stock.Exception.ContactNotFoundException;
import org.gestionstock.stock.Exception.InvalidCredentialsException;
import org.gestionstock.stock.Exception.InventoryItemNotFoundException;
import org.gestionstock.stock.Exception.ItemCategoryNotFoundException;
import org.gestionstock.stock.Exception.UserNotFoundException;
import org.gestionstock.stock.Exception.UsernameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(
        {
            UserNotFoundException.class,
            InventoryItemNotFoundException.class,
            ItemCategoryNotFoundException.class,
            ContactNotFoundException.class,
            CompanyNotFoundException.class
        })
    public ResponseEntity<String> handleNotFoundException(RuntimeException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidCredentialsException.class})
    public ResponseEntity<String> handleBadRequestException(InvalidCredentialsException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleBadRequestException(MethodArgumentNotValidException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameAlreadyExistException.class})
    public ResponseEntity<String> handleConflitException(RuntimeException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

}
