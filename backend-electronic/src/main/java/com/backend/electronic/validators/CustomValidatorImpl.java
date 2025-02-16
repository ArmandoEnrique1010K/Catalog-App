package com.backend.electronic.validators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.backend.electronic.exceptions.ValidationException;
import com.backend.electronic.models.dto.TechSheetDto;

@Service
public class CustomValidatorImpl implements CustomValidator {

    // Validar que el usuario haya subido una imagen
    @Override
    public void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            errors.put("image", "Debe proporcionar una imagen válida");
            throw new ValidationException("Error de validación de imagen", errors);
        }
    }

    // Validar los campos del formulario, definidos con las anotaciones: @NotBlank,
    // @Size, @NotNull, etc. (La dependencia Validation)
    @Override
    public void validateFields(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            throw new ValidationException("Error de validación de campos", errors);
        }
    }

    // Validar que no exista una relación existente entre categoria y caracteristica
    @Override
    public void validateExistingRelation(Boolean existingRelation) {
        if (existingRelation.equals(true)) {
            Map<String, String> errors = new HashMap<>();
            errors.put("field", "La relación entre la categoria y caracteristica ya existe...");
            throw new ValidationException("Error de validación de campos", errors);

        }
    }

    // Validar que no exista un par de caracteristica y valor en la petición.
    @Override
    public void validateExistingPairFeatureValue(List<TechSheetDto> techSheet) {

        // Mapa para almacenar cada característica con su respectivo valor
        Map<String, String> featureMap = new HashMap<>();

        // SI EL PAR DE CARACTERISTICA Y VALOR, UNA CARACTERISTICA SE REPITE, ENTONCES
        // DEBE MOSTRAR UN MENSAJE DE ERROR
        // if (techSheet.) {
        // Map<String, String> errors = new HashMap<>();
        // errors.put("field",
        // "Hay redundancia porque una caracteristica tiene 2 valores asignados,
        // solamente asigne un valor.");
        // throw new ValidationException("Error de redundancia de caracteristica-valor",
        // errors);
        // }

        for (TechSheetDto dto : techSheet) {
            String feature = dto.getFeature();
            String value = dto.getValue();

            // Si la característica ya está en el mapa y el valor es diferente, hay un error
            if (featureMap.containsKey(feature) && !featureMap.get(feature).equals(value)) {
                throw new ValidationException("Error de redundancia de característica-valor",
                        Map.of("field", "La característica '" + feature + "' tiene valores duplicados: '"
                                + featureMap.get(feature) + "' y '" + value + "'. Solo debe tener un valor."));
            }

            // Agregar la característica y su valor al mapa
            featureMap.put(feature, value);
        }

    }
}
