package com.beautyzone.beautysalonapp.exception;

public class CustomValidationException extends Exception{
    public CustomValidationException(String errorMessage){
        super(errorMessage);
    }
}