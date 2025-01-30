package com.backend.catalogapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.catalogapp.models.dto.ProductDetailDto;
import com.backend.catalogapp.models.dto.ProductsListDto;
import com.backend.catalogapp.models.entities.Product;
import com.backend.catalogapp.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ES NORMAL QUE EN LA CONSOLA SE MUESTRE VARIAS CONSULTAS A LA BASE DE DATOS
    // (SEGUN LA CANTIDAD DE PRODUCTOS)
    @GetMapping
    public List<ProductsListDto> list() {
        return productService.findAllByStatusTrue();
    }

    @GetMapping("/{id}")
    public ProductDetailDto findById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result,
            @PathVariable Long id) {

        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<ProductDetailDto> o = productService.update(product, id);

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<ProductDetailDto> o = productService.findById(id);

        if (o.isPresent()) {
            productService.remove(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    // TODO: Tener en cuenta los siguientes endpoints

    // Obtener todos los productos (solamente habilitados)

    // Obtener un producto por su ID
    // Crear un producto
    // Editar un producto
    // Eliminar un producto (cambiar el state a false)

    // Buscar productos por nombre
    // Buscar productos por categoria

}
