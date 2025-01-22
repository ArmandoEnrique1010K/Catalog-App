package com.backend.catalogapp.services;

import java.util.List;

import com.backend.catalogapp.models.dto.ProductDto;

public interface ProductService {

    // Metodos relacionados con productos
    public List<ProductDto> findAllByStatusTrue();

}
