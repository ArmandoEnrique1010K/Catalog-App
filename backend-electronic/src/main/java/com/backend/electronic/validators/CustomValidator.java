package com.backend.electronic.validators;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.backend.electronic.models.dto.TechSheetDto;

// En esta carpeta se definen las validaciones que se van a aplicar en el JSON de las peticiones de tipo POST y PUT
public interface CustomValidator {
    public void validateImage(MultipartFile image);

    public void validateFields(BindingResult result);

    public void validateExistingRelation(Boolean existingRelation);

    public void validateExistingPairFeatureValue(List<TechSheetDto> techSheet);
}
