package com.backend.catalogapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.catalogapp.models.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Lista todos las categorias por status igual a true
    List<Category> findByStatusTrue();

}
