package com.authorize.userauthentication.exceptionHandler.exceptions;

import com.authorize.userauthentication.exceptionHandler.ApiException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException(String message, HttpStatus status) {
        super(message, status);
    }
}
