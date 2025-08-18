package com.Pdt.boxApiService.exception;

public class BoxNotFoundException extends RuntimeException {
    public BoxNotFoundException(String message) {
        super(message);
    }
}
