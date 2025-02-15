package com.backend.electronic.services.validations;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

public interface ValidationService {
    public void validateImage(MultipartFile image);

    public void validateFields(BindingResult result);

    public void validateExistingRelation(Boolean existingRelation);
}
