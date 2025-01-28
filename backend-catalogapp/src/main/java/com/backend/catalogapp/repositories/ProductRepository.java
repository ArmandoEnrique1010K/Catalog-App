package com.backend.catalogapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.catalogapp.models.entities.Product;

// No se utiliza la anotación @Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category
    // WHERE p.status = true")
    List<Product> findByStatusTrue();

}
