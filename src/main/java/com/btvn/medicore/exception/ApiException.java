package com.btvn.medicore.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}