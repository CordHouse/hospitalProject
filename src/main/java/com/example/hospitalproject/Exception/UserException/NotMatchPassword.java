package com.example.hospitalproject.Exception.UserException;

public class NotMatchPassword extends RuntimeException{
    public NotMatchPassword(String message) {
        super(message);
    }
}
