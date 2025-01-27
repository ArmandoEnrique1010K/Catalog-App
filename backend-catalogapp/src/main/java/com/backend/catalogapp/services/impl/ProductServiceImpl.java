package com.backend.catalogapp.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.catalogapp.models.dto.ProductDetailDto;
import com.backend.catalogapp.models.dto.ProductsListDto;
import com.backend.catalogapp.models.dto.mapper.ProductDtoMapper;
import com.backend.catalogapp.models.entities.Product;
import com.backend.catalogapp.repositories.ProductRepository;
import com.backend.catalogapp.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDtoMapper productDtoMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ProductsListDto> findAllByStatusTrue() {
        List<Product> products = productRepository.findByStatusTrue();
        return products.stream().map(
                productDtoMapper::toListDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProductDetailDto> findById(Long id) {
        return Optional.ofNullable(productRepository.findById(id).map(productDtoMapper::toDetailDto).orElseThrow());
    }

    @Transactional
    @Override
    public ProductDetailDto save(Product product) {
        // Se establece los valores en todos los campos
        product.setName(product.getName());
        product.setCode(product.getCode());
        product.setInOffer(product.getInOffer());
        product.setPrice(product.getPrice());

        // Si el producto esta en oferta se establece el precio de oferta
        if (product.getInOffer()) {
            product.setOfferPrice(product.getOfferPrice());
        }

        product.setDescription(product.getDescription());
        product.setStatus(true);

        // Fechas de creación y actualización
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return productDtoMapper.toDetailDto(productRepository.save(product));
    }

    @Override
    public Optional<ProductDetailDto> update(Product product, Long id) {
        Optional<Product> optional = productRepository.findById(id);

        Product productOptional = null;

        if (optional.isPresent()) {
            Product productDb = optional.orElseThrow();
            productDb.setName(product.getName());
            productDb.setCode(product.getCode());
            productDb.setInOffer(product.getInOffer());
            productDb.setPrice(product.getPrice());

            // Si el producto esta en oferta se establece el precio de oferta
            if (productDb.getInOffer()) {
                productDb.setOfferPrice(product.getOfferPrice());
            } else {
                productDb.setOfferPrice(null);
            }

            productDb.setDescription(product.getDescription());

            // Fecha de actualización
            productDb.setUpdatedAt(LocalDateTime.now());

            productOptional = productRepository.save(productDb);
        }

        return Optional.ofNullable(productOptional).map(productDtoMapper::toDetailDto);

    }

    @Override
    public void remove(Long id) {
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isPresent()) {
            Product productDb = optional.orElseThrow();
            productDb.setStatus(false);
            productRepository.save(productDb);
        }
    }

}
