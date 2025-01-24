package com.backend.catalogapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.catalogapp.models.dto.BrandDto;
import com.backend.catalogapp.models.entities.Brand;
import com.backend.catalogapp.services.BrandService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/brands")
// TODO: NO USAR "*" EN UN ENTORNO DE PRODUCCIÓN
@CrossOrigin(originPatterns = "*")
public class BrandController {

    @Autowired
    private BrandService brandService;

    // Endpoint para obtener todas las categorias
    @GetMapping
    public List<BrandDto> list() {
        return brandService.findAllByStatusTrue();
    }

    // Guardar una marca
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Brand brand, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.save(brand));
    }

    // Editar una marca
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Brand brand, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<BrandDto> o = brandService.update(brand, id);

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    // Inhabilitar una marca
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        // Busca la marca por su ID
        Optional<BrandDto> o = brandService.findById(id);

        // Si el usuario existe
        if (o.isPresent()) {
            // Llama al servicio para eliminarlo por su id
            brandService.remove(id);
            // noContent es un metodo que devuelve el status 204, no hay contenido
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }

    // Función privada para validar los campos
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
