package com.backend.catalogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.catalogapp.dto.ProductDto;
import com.backend.catalogapp.entities.Product;
import com.backend.catalogapp.mapper.ProductDtoMapper;
import com.backend.catalogapp.repositories.ProductRepository;
import com.backend.catalogapp.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDto> findAllByStatusTrue() {
        List<Product> products = (List<Product>) productRepository.findByStatusTrue();
        return products.stream().map(p -> ProductDtoMapper.builder().setProduct(p).build())
                .collect(Collectors.toList());
    }

}
