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

import com.backend.electronic.models.dto.CategoryFeatureNameDto;
import com.backend.electronic.models.dto.CategoryFeaturesIdsDto;
import com.backend.electronic.services.CategoryFeatureService;
import com.backend.electronic.validators.CustomValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class CategoryFeatureController {

    @Autowired
    private CategoryFeatureService categoryFeatureService;

    @Autowired
    private CustomValidator validationService;

    // TODO: ¿MODIFICAR ESTO?
    @GetMapping("/{id}/features")
    public List<CategoryFeatureNameDto> list(@PathVariable Long id) {
        return categoryFeatureService.findAllByCategoryId(id);
    }

    // ENDPOINT PARA AGREGAR UN NUEVO VALOR A UNA CARACTERISTICA CORRESPONDIENTE AL
    // ID

    // TODO: ARREGLAR EL MENSAJE DE ERROR
    @PostMapping("/features")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryFeaturesIdsDto categoryFeaturesIdsDto,
            BindingResult result) {

        validationService.validateFields(result);

        CategoryFeaturesIdsDto savedCategoryFeature = categoryFeatureService
                .save(categoryFeaturesIdsDto);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoryFeature);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al asignar categoria con caracteristica : " + ex.getMessage());
        }
    }
}
