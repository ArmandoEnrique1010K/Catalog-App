package com.backend.electronic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.electronic.models.dto.CategoryDto;
import com.backend.electronic.models.dto.FeatureDto;
import com.backend.electronic.models.entities.Category;
import com.backend.electronic.models.entities.Feature;
import com.backend.electronic.services.features.FeatureService;
import com.backend.electronic.services.validations.ValidationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/features")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @Autowired
    private ValidationService validationService;

    @GetMapping
    public List<FeatureDto> list() {
        return featureService.findAllByStatusTrue();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Feature feature, BindingResult result) {
        // if (result.hasErrors()) {
        // return validation(result);
        // }
        validationService.validateFields(result);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(featureService.save(feature));
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el producto: " + ex.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Feature feature, BindingResult result,
            @PathVariable Long id) {

        // if (result.hasErrors()) {
        // return validation(result);
        // }
        validationService.validateFields(result);

        try {
            Optional<FeatureDto> o = featureService.update(feature, id);

            // if (o.isPresent()) {
            // return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
            // }

            return o.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());

        } catch (DataIntegrityViolationException ex) {
            throw ex;
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el producto: " + ex.getMessage());
        }

        // return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<FeatureDto> o = featureService.findById(id);

        if (o.isPresent()) {
            featureService.disable(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }

}
