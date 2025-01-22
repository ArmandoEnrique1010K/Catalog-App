package com.backend.catalogapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.catalogapp.models.entities.Product;

// No se utiliza la anotación @Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStatusTrue();

}
