package com.Pdt.boxApiService.exception;

public class LowBatteryException extends RuntimeException {
    public LowBatteryException(String message) {
        super(message);
    }
}
