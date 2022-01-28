package com.alkemy.disney.exception;

public class MovieException extends RuntimeException {
    public MovieException(String errorMsg) {
        super(errorMsg);
    }
}
