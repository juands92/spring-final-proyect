package com.cev.finalproyect.proyectservices.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Devuelve un mensaje personalizado en caso de violación de integridad de datos (correo electrónico duplicado)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email already registered");
    }
}
