package com.btvn.medicore.exception;

public class UnauthorizedException
        extends RuntimeException {

    public UnauthorizedException(
            String message
    ) {
        super(message);
    }
}