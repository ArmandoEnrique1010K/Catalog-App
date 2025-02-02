package com.backend.electronic.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicateEntryException(DataIntegrityViolationException ex) {
        String errorMessage = ex.getMostSpecificCause().getMessage();
        Map<String, String> errors = new HashMap<>();

        if (errorMessage.contains("Duplicate entry")) {
            Pattern pattern = Pattern.compile("for key '(.+?)'");
            Matcher matcher = pattern.matcher(errorMessage);

            if (matcher.find()) {
                String constraintName = matcher.group(1);
                String fieldName = extractFieldName(constraintName);
                errors.put(fieldName, "El campo " + fieldName + " ya está en uso. Debe ser único.");
            } else {
                errors.put("error_general", "Error de integridad de datos: entrada duplicada.");
            }
        } else {
            errors.put("error_general", "Error de integridad de datos.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    private String extractFieldName(String constraintName) {
        // TODO: OBTENER EN MYSQL ALGO???
        if (constraintName.contains("brand")) {
            return "name";
        } else if (constraintName.contains("product")) {
            return "code";
        } else {
            return "campo_desconocido";
        }
    }
}