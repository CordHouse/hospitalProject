package com.example.hospitalproject.Exception.UserException;

public class NotFoundUsernameException extends RuntimeException {

    public NotFoundUsernameException(String message) {
        super(message);
    }
}
