package com.humberto.api.videostream.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity handleErro404() {
            return ResponseEntity.notFound().build();
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity handleErro400(MethodArgumentNotValidException ex) {
            var erros = ex.getFieldErrors();
            return ResponseEntity.badRequest().body(erros.stream().map(ErrorValidations::new).toList());
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity handleErro400(HttpMessageNotReadableException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        @ExceptionHandler(ValidationException.class)
        public ResponseEntity handleErroRegraDeNegocio(ValidationException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity handleErro500(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
        }

        private record ErrorValidations(String campo, String message) {
            public ErrorValidations(FieldError erro) {
                this(erro.getField(), erro.getDefaultMessage());
            }
        }

}
