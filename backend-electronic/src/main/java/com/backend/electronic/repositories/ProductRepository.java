package com.backend.electronic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.electronic.models.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // CONSULTAS NECESARIAS (Se consideran productos habilitados ):
    // - Obtener todos los productos
    // - " " " " por su nombre
    // - " " " " en oferta
    // - " " " " por su categoria

    // El uso de JOIN FETCH acelera la consulta a la base de datos
    // @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category
    // WHERE p.status = true")

    @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category JOIN FETCH p.image WHERE p.status = true AND p.brand.status = true AND p.category.status = true")
    List<Product> findAllProducts();

    @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category JOIN FETCH p.image WHERE p.status = true AND p.brand.status = true AND p.category.status = true AND p.name LIKE %:name%")
    List<Product> findAllProductsByName(@Param("name") String name);

    @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category JOIN FETCH p.image WHERE p.status = true AND p.brand.status = true AND p.category.status = true AND p.inOffer = true")
    List<Product> findAllProductsByOffer();

    @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category JOIN FETCH p.image WHERE p.status = true AND p.brand.status = true AND p.category.status = true AND p.category.id = :id")
    List<Product> findAllProductsByCategoryId(@Param("id") Long id);

    // TODO: UNA VEZ TERMINADO, IMPLEMENTAR PAGINACION
}

// EL USO DE FETCH EN UN QUERY
// https://medium.com/javarevisited/spring-jpa-when-to-use-join-fetch-a6cec898c4c6