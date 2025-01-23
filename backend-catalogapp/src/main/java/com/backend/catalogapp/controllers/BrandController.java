package com.backend.catalogapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.catalogapp.models.dto.BrandDto;
import com.backend.catalogapp.services.BrandService;

@RestController
@RequestMapping("/brands")
// TODO: NO USAR "*" EN UN ENTORNO DE PRODUCCIÓN
@CrossOrigin(originPatterns = "*")
public class BrandController {

    @Autowired
    private BrandService brandService;

    // Endpoint para obtener todas las categorias
    @GetMapping
    public List<BrandDto> listBrands() {
        return brandService.findAll();
    }

}
