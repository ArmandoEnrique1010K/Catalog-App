package com.backend.electronic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.requests.TechSheetRequestDto;
import com.backend.electronic.services.products.features.ProductFeatureService;

@RestController
@RequestMapping("/product/feature")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class ProductFeatureController {

    @Autowired
    private ProductFeatureService productFeatureService;

    // OBTIENE LA FICHA TECNICA DE UN PRODUCTO POR SU ID
    @GetMapping("/{id}/tech-sheet")
    public ResponseEntity<List<TechSheetDto>> getTechSheet(@PathVariable Long id) {
        return ResponseEntity.ok(productFeatureService.getTechSheet(id));
    }

    // TODO: MODIFICAR ESTO
    // GUARDA LA FICHA TECNICA DE UN PRODUCTO
    @PostMapping
    public ResponseEntity<String> saveTechSheet(@RequestBody TechSheetRequestDto request) {
        productFeatureService.saveTechSheet(request);
        return ResponseEntity.ok("Ficha técnica guardada exitosamente.");
    }

}
