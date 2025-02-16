package com.backend.electronic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.electronic.models.dto.FeatureDto;
import com.backend.electronic.models.dto.FeatureValueDto;
import com.backend.electronic.models.entities.FeatureValue;
import com.backend.electronic.services.FeatureService;
import com.backend.electronic.services.FeatureValueService;
import com.backend.electronic.validators.CustomValidator;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/features")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class FeatureValueController {

    @Autowired
    private FeatureValueService featureValueService;

    @Autowired
    private CustomValidator validationService;

    @Autowired
    private FeatureService featureService;

    // VALORES DE UNA CARACTERISTICA
    // SI LA CARACTERISTICA POR EL ID TIENE UN STATUS FALSE, ENTONCES NO VA A
    // MOSTRAR LOS VALORES
    @GetMapping("/{id}/values")
    public ResponseEntity<?> list(@PathVariable Long id) {

        Optional<FeatureDto> feature = featureService.findById(id);

        if (feature.isPresent()) {
            return ResponseEntity.ok(featureValueService.findAllByFeatureId(id));
        }

        return ResponseEntity.notFound().build(); // Retorna 404 Not Found
    }

    // PUEDE SERVIR...
    // ENDPOINT PARA MOSTRAR LOS VALORES QUE CORRESPONDEN A UNA CARACTERISTICA Y
    // CUYOS VALORES SEAN DISTINTOS A LOS QUE SE ENCUENTRAN EN LOS PRODUCTOS QUE
    // CORRESPONDEN A ESA CATEGORIA

    // TODO: CAMBIAR EL NOMBRE DEL METODO
    @GetMapping("/{featureId}/values/category/{categoryId}")
    public ResponseEntity<?> listDistintValuesByFeatureAndCategory(@PathVariable Long featureId,
            @PathVariable Long categoryId) {

        List<?> result = featureValueService.getFeatureValuesByCategoryAndFeature(categoryId, featureId);

        // 204 ES EL STATUS SI NO HAY CONTENIDO
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    // ENDPOINT PARA AGREGAR UN NUEVO VALOR A UNA CARACTERISTICA CORRESPONDIENTE AL
    // ID
    @PostMapping("/{id}/values")
    public ResponseEntity<?> create(@PathVariable Long id, @Valid @RequestBody FeatureValue featureValue,
            BindingResult result) {

        validationService.validateFields(result);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(featureValueService.save(featureValue, id));
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el producto: " + ex.getMessage());
        }
    }

}
