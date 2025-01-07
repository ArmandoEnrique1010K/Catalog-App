package com.backend.catalogapp.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.catalogapp.dto.ProductDto;
import com.backend.catalogapp.entities.Product;
import com.backend.catalogapp.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public List<ProductDto> findAllByStatusTrue() {
        throw new UnsupportedOperationException("Unimplemented method 'findAllByStatusTrue'");
    }

}
