package com.humberto.api.videostream.infra;

public class ValidationException extends RuntimeException {
    public ValidationException(String mensagem) {
        super(mensagem);
    }
}
