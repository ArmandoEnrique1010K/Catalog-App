package com.backend.electronic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.electronic.models.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    // No se utiliza un @Query para especificar la consulta ya que se esta
    // utilizando el nombre del metodo

    // Lista todos las marcas por status igual a true
    List<Brand> findByStatusTrue();

    // Lista todas las marcas distintas de todos los productos que corresponden a la
    // misma categoria
    @Query("SELECT DISTINCT b FROM Brand b JOIN b.products p JOIN p.category c WHERE c.id = :categoryId AND b.status = true")
    List<Brand> findDistinctByCategoryId(@Param("categoryId") Long categoryId);

}

// https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
