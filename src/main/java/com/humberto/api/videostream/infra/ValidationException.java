package com.humberto.api.videostream.infra;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
