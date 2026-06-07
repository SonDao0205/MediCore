package com.btvn.medicore.exception;

public class ForbiddenException
        extends RuntimeException {

    public ForbiddenException(
            String message
    ) {
        super(message);
    }
}