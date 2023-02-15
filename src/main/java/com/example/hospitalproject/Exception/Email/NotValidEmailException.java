package com.example.hospitalproject.Exception.Email;

public class NotValidEmailException extends RuntimeException{
    public NotValidEmailException(String message){
        super(message);
    }
}
