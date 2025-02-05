package com.backend.electronic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.electronic.models.dto.CategoryFeatureDto;
import com.backend.electronic.models.dto.FeatureValueDto;
import com.backend.electronic.models.entities.CategoryFeature;
import com.backend.electronic.models.entities.FeatureValue;
import com.backend.electronic.services.categories.features.CategoryFeatureService;
import com.backend.electronic.services.features.values.FeatureValueService;
import com.backend.electronic.services.validations.ValidationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class CategoryFeatureController {

    @Autowired
    private CategoryFeatureService categoryFeatureService;

    @Autowired
    private ValidationService validationService;

    // TODO: ¿MODIFICAR ESTO?
    @GetMapping("/{id}/features")
    public List<CategoryFeatureDto> list(@PathVariable Long id) {
        return categoryFeatureService.findAllByCategoryId(id);
    }

    // ENDPOINT PARA AGREGAR UN NUEVO VALOR A UNA CARACTERISTICA CORRESPONDIENTE AL
    // ID
    @PostMapping("/features")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryFeature categoryFeature,
            BindingResult result) {

        validationService.validateFields(result);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryFeatureService
                    .save(categoryFeature.getCategory().getId(), categoryFeature.getFeature().getId()));
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el producto: " + ex.getMessage());
        }
    }
}
