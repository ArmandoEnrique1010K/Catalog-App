package com.backend.electronic.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ValidationsByFields {

    public ResponseEntity<?> validationByEmptyImage() {
        Map<String, String> errors = new HashMap<>();
        errors.put("image", "Debe proporcionar una imagen válida");
        return ResponseEntity.badRequest().body(errors);
    }

    public ResponseEntity<?> validationByFields(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
