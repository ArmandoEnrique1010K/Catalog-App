package com.backend.catalogapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.catalogapp.models.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    // Lista todos las marcas por status igual a true
    List<Brand> findByStatusTrue();

    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
}
