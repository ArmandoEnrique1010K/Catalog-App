package com.backend.electronic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.electronic.models.dto.ProductTechSheetDto;
import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.entities.ProductFeature;
import com.backend.electronic.services.ProductFeatureService;

@RestController
@RequestMapping("/product")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class ProductFeatureController {

    @Autowired
    private ProductFeatureService productFeatureService;

    // OBTIENE LA FICHA TECNICA DE UN PRODUCTO POR SU ID
    @GetMapping("/{id}/feature/tech-sheet")
    public ResponseEntity<List<TechSheetDto>> getTechSheet(@PathVariable Long id) {

        // CAMBIA EL STATUS A 204, NO CONTENT
        if (productFeatureService.getTechSheet(id).isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(productFeatureService.getTechSheet(id));
        }

    }

    // TODO: MODIFICAR ESTO
    // GUARDA LA FICHA TECNICA DE UN PRODUCTO

    // ESTO ESTA DE PRUEBA, PUES LA FICHA TECNICA SE DEBE GUARDAR CUANDO SE GUARDA
    // UN PRODUCTO O SE ACTUALIZA UN PRODUCTO
    @PostMapping("/{id}/feature/tech-sheet")
    public ResponseEntity<ProductTechSheetDto> saveTechSheet(@PathVariable Long id,
            @RequestBody List<TechSheetDto> techSheet) {
        ProductTechSheetDto result = productFeatureService.saveTechSheet(id, techSheet);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/feature/tech-sheet")
    public ResponseEntity<Optional<ProductTechSheetDto>> updateTechSheet(@PathVariable Long id,
            @RequestBody List<TechSheetDto> techSheet) {
        Optional<ProductTechSheetDto> result = productFeatureService.updateTechSheet(id, techSheet);

        if (result.isPresent()) {
            return ResponseEntity.ok(result);

        } else {
            return ResponseEntity.notFound().build();
        }

    }
    // No conviene exponer la entidad
    // @PostMapping("/{id}/feature/tech-sheet/test2")
    // public ResponseEntity<String> testsaveTechSheet2(@PathVariable Long id,
    // @RequestBody List<ProductFeature> techSheet) {
    // productFeatureService.saveTechSheet2(id, techSheet);
    // return ResponseEntity.ok("Ficha técnica guardada exitosamente.");
    // }

}
