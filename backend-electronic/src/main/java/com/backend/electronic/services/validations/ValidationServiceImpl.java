package com.backend.electronic.services.validations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.backend.electronic.exceptions.ValidationException;

@Service
public class ValidationServiceImpl implements ValidationService {
    public void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            errors.put("image", "Debe proporcionar una imagen válida");
            throw new ValidationException("Error de validación de imagen", errors);
        }
    }

    public void validateFields(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            throw new ValidationException("Error de validación de campos", errors);
        }
    }

    public void validateExistingRelation(Boolean existingRelation) {
        if (existingRelation.equals(true)) {
            Map<String, String> errors = new HashMap<>();
            errors.put("field", "La relación entre la categoria y caracteristica ya existe...");
            throw new ValidationException("Error de validación de campos", errors);

        }
    }
}
