package com.backend.catalogapp.services;

import java.util.List;

import com.backend.catalogapp.models.dto.BrandDto;

public interface BrandService {

    // Listar todas las marcas
    public List<BrandDto> findAll();
}
