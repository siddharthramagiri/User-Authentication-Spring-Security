package com.authorize.userauthentication.exceptionHandler;

import com.authorize.userauthentication.exceptionHandler.exceptions.UserAlreadyExistsException;
import com.authorize.userauthentication.exceptionHandler.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String error) {}

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleException(ApiException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorResponse(ex.getMessage()));
    }
}
