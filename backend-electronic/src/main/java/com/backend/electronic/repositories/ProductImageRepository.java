package com.backend.electronic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.electronic.models.entities.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
