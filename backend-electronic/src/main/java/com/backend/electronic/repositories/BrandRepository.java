package com.backend.electronic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.electronic.models.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    // No se utiliza un @Query para especificar la consulta ya que se esta
    // utilizando el nombre del metodo

    // Lista todos las marcas por status igual a true
    List<Brand> findByStatusTrue();

}

// https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
