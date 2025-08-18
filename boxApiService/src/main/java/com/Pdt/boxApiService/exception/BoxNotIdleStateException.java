package com.Pdt.boxApiService.exception;

public class BoxNotIdleStateException extends RuntimeException {
    public BoxNotIdleStateException(String message) {
        super(message);
    }
}
