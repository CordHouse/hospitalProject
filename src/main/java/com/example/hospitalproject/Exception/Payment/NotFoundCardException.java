package com.example.hospitalproject.Exception.Payment;

public class NotFoundCardException extends RuntimeException{
    public NotFoundCardException(String message){
        super(message);
    }
}
