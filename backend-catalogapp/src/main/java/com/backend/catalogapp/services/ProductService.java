package com.backend.catalogapp.services;

import java.util.List;
import java.util.Optional;

import com.backend.catalogapp.models.dto.ProductDetailDto;
import com.backend.catalogapp.models.dto.ProductsListDto;
import com.backend.catalogapp.models.entities.Product;

public interface ProductService {

    // Se utiliza la lista de productos
    public List<ProductsListDto> findAllByStatusTrue();

    public Optional<ProductDetailDto> findById(Long id);

    public ProductDetailDto save(Product product);

    public Optional<ProductDetailDto> update(Product product, Long id);

    public void remove(Long id);

}
