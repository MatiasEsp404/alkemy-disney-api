package com.alkemy.disney.exception;

public class RegisterException extends RuntimeException {
    public RegisterException(String errorMsg) {
        super(errorMsg);
    }
}