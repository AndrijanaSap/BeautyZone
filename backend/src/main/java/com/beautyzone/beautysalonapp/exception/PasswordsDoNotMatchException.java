package com.beautyzone.beautysalonapp.exception;

public class PasswordsDoNotMatchException extends RuntimeException{
    public PasswordsDoNotMatchException(String message){
        super(message);
    }
}