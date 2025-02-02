package com.backend.electronic.exceptions;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException {
    private final BindingResult result;

    public ValidationException(BindingResult result) {
        this.result = result;
    }

    public BindingResult getResult() {
        return result;
    }
}
