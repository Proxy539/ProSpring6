package com.apress.prospring6.fifteen.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<HttpStatus> handleBadRequest(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().build();
    }
}
