package com.Pdt.boxApiService.exception;

public class ItemRequestsNullOrEmptyException extends RuntimeException {
    public ItemRequestsNullOrEmptyException(String message) {
        super(message);
    }
}