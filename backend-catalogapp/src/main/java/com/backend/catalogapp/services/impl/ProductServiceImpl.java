package com.backend.catalogapp.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.catalogapp.models.dto.ProductDetailDto;
import com.backend.catalogapp.models.dto.ProductsListDto;
import com.backend.catalogapp.models.dto.mapper.ProductDtoMapper;
import com.backend.catalogapp.models.entities.Brand;
import com.backend.catalogapp.models.entities.Category;
import com.backend.catalogapp.models.entities.Image;
import com.backend.catalogapp.models.entities.Product;
import com.backend.catalogapp.repositories.BrandRepository;
import com.backend.catalogapp.repositories.CategoryRepository;
import com.backend.catalogapp.repositories.ImageRepository;
import com.backend.catalogapp.repositories.ProductRepository;
import com.backend.catalogapp.services.ImageService;
import com.backend.catalogapp.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductDtoMapper productDtoMapper;

    @Autowired
    private ImageService imageService;

    @Transactional(readOnly = true)
    @Override
    public List<ProductsListDto> findAllByStatusTrue() {
        List<Product> products = productRepository.findAllProducts();
        return products.stream().map(
                productDtoMapper::toListDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProductDetailDto> findById(Long id) {
        return Optional.ofNullable(productRepository.findById(id).map(productDtoMapper::toDetailDto).orElseThrow());
    }

    @Transactional
    @Override
    public ProductDetailDto save(Product product) {

        Brand brand = brandRepository.findById(product.getBrand().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La marca con ID " + product.getBrand().getId() + " no existe"));
        product.setBrand(brand);

        // Obtener y asignar la categoría
        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La categoría con ID " + product.getCategory().getId() + " no existe"));
        product.setCategory(category);

        // MANEJO DE LA IMAGEN
        // String nameImage = imageService.storeImage(product.getImage().getFile());
        // product.getImage().setName(nameImage);

        // Guardar primero la imagen
        Image image = new Image();
        String nameImage = imageService.storeImage(product.getImage().getFile());
        image.setName(nameImage);
        image = imageRepository.save(image); // Guardar la imagen antes de asignarla al producto
        product.setImage(image);

        // Asignar valores directos
        product.setName(product.getName());
        product.setCode(product.getCode());
        product.setInOffer(product.getInOffer());
        product.setPrice(product.getPrice());

        if (product.getInOffer()) {
            product.setOfferPrice(product.getOfferPrice());
        }

        product.setDescription(product.getDescription());
        product.setStatus(true);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        // Guardar el producto
        return productDtoMapper.toDetailDto(productRepository.save(product));
    }

    @Override
    public Optional<ProductDetailDto> update(Product product, Long id) {

        Brand brand = brandRepository.findById(product.getBrand().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La marca con ID " + product.getBrand().getId() + " no existe"));

        // Obtener y asignar la categoría
        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La categoría con ID " + product.getCategory().getId() + " no existe"));

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

            productDb.setBrand(brand);
            productDb.setCategory(category);

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
