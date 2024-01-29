package com.example.BookService.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController
{
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> notFound(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
