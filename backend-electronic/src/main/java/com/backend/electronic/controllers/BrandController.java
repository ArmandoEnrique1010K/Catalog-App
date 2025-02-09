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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.electronic.models.dto.BrandDto;
import com.backend.electronic.models.entities.Brand;
import com.backend.electronic.services.brands.BrandService;
import com.backend.electronic.services.validations.ValidationService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/brands")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ValidationService validationService;

    // Endpoint para todas las categorias habilitadas
    @GetMapping
    public List<BrandDto> list() {
        return brandService.findAllByStatusTrue();
    }

    // Guardar una marca
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Brand brand, BindingResult result) {
        // if (result.hasErrors()) {
        // return validation(result);
        // }
        validationService.validateFields(result);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(brandService.save(brand));
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el producto: " + ex.getMessage());
        }

    }

    // Editar una marca
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Brand brand, BindingResult result, @PathVariable Long id) {

        // if (result.hasErrors()) {
        // return validation(result);
        // }
        validationService.validateFields(result);

        try {
            Optional<BrandDto> o = brandService.update(brand, id);
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

    // Inhabilitar una marca
    @PatchMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        // Busca la marca por su ID
        Optional<BrandDto> o = brandService.findById(id);

        // Si el usuario existe
        if (o.isPresent()) {
            // Llama al servicio para eliminarlo por su id
            brandService.disable(id);
            // noContent es un metodo que devuelve el status 204, no hay contenido
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }

    // NO UTILIZAR ESTO, EN SU LUGAR CONVIENE USAR ValidationService
    // Función privada para validar los campos
    // private ResponseEntity<?> validation(BindingResult result) {
    // Map<String, String> errors = new HashMap<>();

    // result.getFieldErrors().forEach(err -> {
    // errors.put(err.getField(), "El campo " + err.getField() + " " +
    // err.getDefaultMessage());
    // });
    // return ResponseEntity.badRequest().body(errors);
    // }

}
