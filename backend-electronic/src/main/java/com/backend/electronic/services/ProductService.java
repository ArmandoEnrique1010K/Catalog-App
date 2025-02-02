package com.backend.electronic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.backend.electronic.models.dto.ProductDetailDto;
import com.backend.electronic.models.dto.ProductsListDto;
import com.backend.electronic.models.entities.Product;

public interface ProductService {

    // Se utiliza la lista de productos
    public List<ProductsListDto> findAllByStatusTrue();

    public List<ProductsListDto> findAllByName(String name);

    public Optional<ProductDetailDto> findById(Long id);

    public ProductDetailDto save(Product product, MultipartFile file);

    public Optional<ProductDetailDto> update(Product product, MultipartFile file, Long id);

    public void remove(Long id);

    // TODO: CREAR SERVICIO PARA OBTENER LOS PRODUCTOS QUE CORRESPONDEN A UNA MISMA
    // CATEGORIA
}
