package com.backend.catalogapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.catalogapp.models.entities.Product;

// No se utiliza la anotación @Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category
    // WHERE p.status = true")
    List<Product> findByStatusTrue();

    // QUERY PARA SELECCIONAR TODOS LOS PRODUCTOS HABILITADOS (POR MARCA Y CATEGORIA
    // A LA VEZ)
    // @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category
    // WHERE p.status = true AND p.brand.id = :brandId AND p.category.id =
    // :categoryId")
    @Query("SELECT p FROM Product p JOIN p.brand JOIN p.category WHERE p.status = true AND p.brand.status = true AND p.category.status = true")
    List<Product> findAll();

    // QUERY PARA SELECCIONAR TODOS LOS PRODUCTOS HABILITADOS (POR MARCA Y
    // CATEGORIA) EN OFERTA
    @Query("SELECT p FROM Product p JOIN p.brand JOIN p.category WHERE p.status = true AND p.brand.status = true AND p.category.status = true AND p.inOffer = true")
    List<Product> findByOffer();

    // LISTAR PRODUCTOS POR SU NOMBRE (TIPO BARRA DE BUSQUEDA)
    // @Query("SELECT p FROM Product p JOIN p.brand JOIN p.category WHERE p.status =
    // true AND p.brand.status = true AND p.category.status = true AND p.name LIKE
    // %:p_name%")
    // List<Product> findByName(@Param("p_name") String p_name);

    @Query("SELECT p FROM Product p WHERE p.status = true AND p.brand.status = true AND p.category.status = true AND p.name LIKE %:name%")
    List<Product> findByName(@Param("name") String name);

    // List<Product> findAllBy

    // TODO: IMPLEMENTAR PAGINACION

}
